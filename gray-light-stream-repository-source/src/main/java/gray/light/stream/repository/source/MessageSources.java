package gray.light.stream.repository.source;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.endpoint.AbstractMessageSource;

import java.util.function.Supplier;

/**
 * 根据需要创建不同的消息源
 *
 * @author XyParaCrim
 */
public final class MessageSources {


    public static <T> MessageSource<T> delegated(String componentType, Supplier<T> supplier) {
        return new DelegatedMessageSource(componentType, supplier);
    }

    /**
     * 委托一个Supplier方法，提供消息
     *
     * @param <T> 消息类型
     */
    @RequiredArgsConstructor
    private static class DelegatedMessageSource<T> extends AbstractMessageSource<T> {

        private final String componentType;

        private final Supplier<T> supplier;

        @Override
        protected Object doReceive() {
            return supplier.get();
        }

        @Override
        public String getComponentType() {
            return componentType;
        }
    }

}
