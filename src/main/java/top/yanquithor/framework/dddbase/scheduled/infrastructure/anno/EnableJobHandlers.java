package top.yanquithor.framework.dddbase.scheduled.infrastructure.anno;

import org.springframework.context.annotation.Import;
import top.yanquithor.framework.dddbase.scheduled.infrastructure.config.JobHandlerRegistrar;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JobHandlerRegistrar.class)
public @interface EnableJobHandlers {
}