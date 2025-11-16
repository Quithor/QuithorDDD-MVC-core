package top.yanquithor.framework.dddbase.scheduled.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.yanquithor.framework.dddbase.common.domain.interfaces.JobHandler;

import java.util.List;

@Slf4j
@Configuration
@Import(JobHandlerRegistrar.class)
public class AutoScannerConfiguration {
    
    public AutoScannerConfiguration(List<JobHandler> jobList) throws Exception {
        log.info("Register Job Bean Count: {}", jobList.size());
        log.info("AutoScannerConfiguration loaded");
    }
    
}
