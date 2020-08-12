package gray.light.stream.repository.source;

import gray.light.owner.entity.ProjectDetails;
import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 定义检测仓库更新的配置属性
 *
 * @author XyParaCrim
 */
@Data
public class RepositoryPollingUpdateProperties {

    /**
     * 提供需要检测更新提供方法
     */
    private final Supplier<ProjectDetails> source;

    /**
     * 执行周期
     */
    private final long period;

    /**
     * 执行周期的单位
     */
    private final TimeUnit unit;

}
