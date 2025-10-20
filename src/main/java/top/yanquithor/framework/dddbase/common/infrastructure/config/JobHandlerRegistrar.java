package top.yanquithor.framework.dddbase.common.infrastructure.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import top.yanquithor.framework.dddbase.common.domain.interfaces.JobHandler;

import java.beans.Introspector;
import java.lang.reflect.Modifier;

public class JobHandlerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {

        ClassPathScanningCandidateComponentProvider scanner =
            new ClassPathScanningCandidateComponentProvider(false);

        scanner.setResourceLoader(resourceLoader);
        // 只扫描实现了 JobHandler 接口的类
        scanner.addIncludeFilter(new AssignableTypeFilter(JobHandler.class));

        // 使用主类所在包作为 base package（需外部提供）
        String basePackage = determineBasePackage(importingClassMetadata);

        for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
            String className = bd.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(className);
                if (!JobHandler.class.isAssignableFrom(clazz) || 
                    Modifier.isInterface(clazz.getModifiers())) {
                    continue;
                }

                // 自动生成 beanName（首字母小写）
                String beanName = buildBeanName(className);

                // 注册为 BeanDefinition
                if (!registry.containsBeanDefinition(beanName)) {
                    registry.registerBeanDefinition(beanName, bd);
                    System.out.println("Registered JobHandler: " + className);
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + className);
            }
        }
    }

    private String determineBasePackage(AnnotationMetadata metadata) {
        // 默认使用当前配置类所在包
        return metadata.getClassName().substring(0, metadata.getClassName().lastIndexOf('.'));
    }

    private String buildBeanName(String className) {
        String simpleName = className.substring(className.lastIndexOf('.') + 1);
        return Introspector.decapitalize(simpleName);
    }
}