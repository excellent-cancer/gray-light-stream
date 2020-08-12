package gray.light.stream.repository.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * 收集{@link EnableRepositorySource}注册的{@link IntegrationFlowFactory}工厂类，
 * 定义这些工厂类Bean
 *
 * @author XyParaCrim
 */
@Slf4j
public class IntegrationFlowFactoryRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * 收集{@link EnableRepositorySource}注册的{@link IntegrationFlowFactory}工厂类
     *
     * @param metadata 导入类的元数据
     * @param registry 当前Bean的注册机
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry) {
        AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(
                ClassUtils.resolveClassName(metadata.getClassName(), null),
                EnableRepositorySource.class);

        for (Class<? extends IntegrationFlowFactory> factoryType : collectFactoryClasses(attrs, metadata.getClassName())) {

            if (!registry.containsBeanDefinition(factoryType.getName())) {

                if (factoryType.isInterface()) {
                    log.error("Failed to create IntegrationFlowFactory instance: {}" + factoryType.getName());
                } else {

                    RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(factoryType);
                    registry.registerBeanDefinition(factoryType.getName(), rootBeanDefinition);
                }
            }

        }

    }

    /**
     * 收集{@link EnableRepositorySource}注册的工厂类
     *
     * @param attrs 注解属性
     * @param className {@link EnableRepositorySource}所在的类名
     * @return 收集{@link EnableRepositorySource}注册的工厂类
     */
    private Class<? extends IntegrationFlowFactory>[] collectFactoryClasses(AnnotationAttributes attrs, String className) {
        EnableRepositorySource enableRepositorySource = AnnotationUtils.synthesizeAnnotation(attrs,
                EnableRepositorySource.class, ClassUtils.resolveClassName(className, null));
        return enableRepositorySource.value();
    }
}
