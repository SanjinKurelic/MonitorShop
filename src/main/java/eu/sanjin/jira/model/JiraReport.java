package eu.sanjin.jira.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
public class JiraReport {

    private String testCase;
    private String comment;
    private String status;
    private LocalDateTime executionDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

}
