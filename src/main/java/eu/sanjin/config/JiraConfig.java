package eu.sanjin.config;

import lombok.extern.slf4j.Slf4j;
import org.constretto.annotation.Configure;

import java.util.Objects;

@Slf4j
public record JiraConfig(String jiraUrl, String jiraUser, String jiraPassword, Boolean jiraEnabled) {

    private static JiraConfig instance;

    @Configure
    public JiraConfig {
        // This is record constructor sugar used for adding constructor annotations
        log.info("Jira configuration loaded");
    }

    public static JiraConfig getInstance() {
        if (Objects.isNull(instance)) {
            instance = ConstrettoConfig.getConfiguration().as(JiraConfig.class);
        }

        return instance;
    }
}
