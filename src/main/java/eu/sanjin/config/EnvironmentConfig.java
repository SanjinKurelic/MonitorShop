package eu.sanjin.config;

import lombok.extern.slf4j.Slf4j;
import org.constretto.annotation.Configure;

import java.util.Objects;

@Slf4j
public record EnvironmentConfig(String apiUrl, String frontendUrl) {

    private static EnvironmentConfig instance;
    
    @Configure
    public EnvironmentConfig {
        // This is record constructor sugar used for adding constructor annotations
        log.info("Environment configuration loaded");
    }

    public static EnvironmentConfig getInstance() {
        if (Objects.isNull(instance)) {
            instance = ConstrettoConfig.getConfiguration().as(EnvironmentConfig.class);
        }

        return instance;
    }
}
