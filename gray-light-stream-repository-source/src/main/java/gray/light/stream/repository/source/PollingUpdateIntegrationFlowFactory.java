package gray.light.stream.repository.source;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.HashMap;
import java.util.Map;

/**
 * 拉取需要更新仓库，发布更新任务的flow工厂
 *
 * @author XyParaCrim
 */
@EnableBinding(PollingUpdateSourceBinding.class)
@RequiredArgsConstructor
public class PollingUpdateIntegrationFlowFactory implements IntegrationFlowFactory {

    private static final String FLOW_COMPONENT_NAME = "polling-update";

    private final IntegrationFlowContext flowContext;

    private final Map<String, RepositoryPollingUpdateProperties> flowProperties;

    private final PollingUpdateSourceBinding binding;

    private Map<String, IntegrationFlowContext.IntegrationFlowRegistration> flowTable;

    private boolean isRunning = false;

    @Override
    public void start() {
        if (!isRunning && flowProperties.size() > 0) {
            flowTable = new HashMap<>(flowProperties.size());

            for (Map.Entry<String, RepositoryPollingUpdateProperties> entry : flowProperties.entrySet()) {
                String flowName = entry.getKey();
                RepositoryPollingUpdateProperties properties = entry.getValue();
                MessageSource<?> source = delegatedMessageSource(flowName, properties);

                IntegrationFlow flow = IntegrationFlows.
                        from(source, spec -> endpointConfigurer(spec, properties)).
                        channel(binding.output()).
                        get();

                flowTable.put(flowName, flowContext.registration(flow).register());
            }

            isRunning = true;
        }
    }

    @Override
    public void stop() {
        if (isRunning && flowTable != null) {
            flowTable.forEach((name, registration) -> registration.destroy());
            flowTable.clear();
            isRunning = false;
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    private MessageSource<?> delegatedMessageSource(String flowName, RepositoryPollingUpdateProperties properties) {
        String componentType = FLOW_COMPONENT_NAME + ":" + flowName;

        return MessageSources.delegated(componentType, properties.getSource());
    }

    private static void endpointConfigurer(SourcePollingChannelAdapterSpec spec, RepositoryPollingUpdateProperties properties) {
        PollerMetadata pollerMetadata = new PollerMetadata();
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(properties.getPeriod(), properties.getUnit());

        pollerMetadata.setTrigger(periodicTrigger);
        spec.poller(pollerMetadata);
    }
}
