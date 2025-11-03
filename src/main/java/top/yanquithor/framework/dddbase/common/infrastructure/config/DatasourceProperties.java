package top.yanquithor.framework.dddbase.common.infrastructure.config;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource")
public class DatasourceProperties {
    
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    
    private final Environment environment;
    
    public DatasourceProperties(Environment environment) {
        this.environment = environment;
    }
    
    @PostConstruct
    public void initDriverClassName() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (containsProfile(activeProfiles, "pgsql")) {
            this.driverClassName = "org.postgresql.Driver";
        } else if (containsProfile(activeProfiles, "mysql")) {
            this.driverClassName = "org.mariadb.jdbc.Driver";
        } else if (containsProfile(activeProfiles, "sqlite")) {
            this.driverClassName = "org.sqlite.JDBC";
        } else if (containsProfile(activeProfiles, "h2")) {
            this.driverClassName = "org.h2.Driver";
        } else {
            throw new IllegalStateException("No database profile found (pgsql, mysql, sqlite, h2)");
        }
    }
    
    private boolean containsProfile(String[] profiles, String target) {
        for (String profile : profiles) {
            if (target.equals(profile)) {
                return true;
            }
        }
        return false;
    }
}