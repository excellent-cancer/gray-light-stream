package gray.light.stream.repository.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 定义需要更新仓库信息的输出channel和binding
 *
 * @author XyParaCrim
 */
public interface PollingUpdateSourceBinding {

    /**
     * 绑定channel的binding
     *
     * @return 消息频道
     */
    @Output("polling-update")
    MessageChannel output();

}
