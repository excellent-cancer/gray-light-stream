package gray.light.stream.repository.source;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注册Integration flow工厂
 *
 * @author XyParaCrim
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import(IntegrationFlowFactoryRegistrar.class)
public @interface EnableRepositorySource {

    /**
     * 激活的工厂类
     *
     * @return 激活的工厂类
     */
    Class<? extends IntegrationFlowFactory>[] value();

}
