package gray.light.stream.book.source;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * 配置同步book结构项目分发更新任务
 *
 * @author XyParaCrim
 */
@EnableBinding(SynchronizedBookSource.class)
@EnableConfigurationProperties(StreamBookSourceProperties.class)
public class StreamBookSourceConfiguration {
    




}
