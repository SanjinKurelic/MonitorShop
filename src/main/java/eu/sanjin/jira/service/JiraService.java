package eu.sanjin.jira.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sanjin.config.JiraConfig;
import eu.sanjin.config.SslContextConfig;
import eu.sanjin.jira.mapper.JiraMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.auth.AuthenticationException;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.mapstruct.factory.Mappers;
import org.testng.ITestResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
public class JiraService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JiraMapper jiraMapper = Mappers.getMapper(JiraMapper.class);
    private final JiraConfig jiraConfig = JiraConfig.getInstance();

    public void sendResultsToJira(ITestResult testResult) {
        if (Boolean.FALSE.equals(jiraConfig.jiraEnabled())) {
            return;
        }

        var jiraReport = jiraMapper.mapITestResultToJiraReport(testResult);

        try (final var client = getHttpClient()) {
            var request = new HttpPost(jiraConfig.jiraUrl());
            request.setEntity(
                new StringEntity(objectMapper.writeValueAsString(List.of(jiraReport)), ContentType.APPLICATION_JSON));

            var response = client.execute(request, new BasicHttpClientResponseHandler());
            log.info("Test result successfully sent to Jira. Response:\n{}", response);
        } catch (IOException | URISyntaxException | AuthenticationException e) {
            log.error(e.getMessage(), e);
        }
    }

    private CloseableHttpClient getHttpClient() throws AuthenticationException, URISyntaxException {
        var basicScheme = new BasicScheme();
        basicScheme.initPreemptive(
            new UsernamePasswordCredentials(jiraConfig.jiraUser(), jiraConfig.jiraPassword().toCharArray())
        );

        return HttpClients.custom()
            .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create().setSslContext(
                    SslContextConfig.getInstance().getSslContext()).build())
                .build())
            .setDefaultHeaders(List.of(
                new BasicHeader(HttpHeaders.AUTHORIZATION,
                    basicScheme.generateAuthResponse(HttpHost.create(jiraConfig.jiraUrl()), null, null))
            ))
            .evictExpiredConnections()
            .build();
    }

}
