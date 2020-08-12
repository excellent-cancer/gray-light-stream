package gray.light.stream.book.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 已同步的项目结构数据Binding接口
 *
 * @author XyParaCrim
 */
public interface SynchronizedBookSource {

    /**
     * binding获取方法
     *
     * @return 消息频道
     */
    @Output("synchronized-book")
    MessageChannel output();

}
