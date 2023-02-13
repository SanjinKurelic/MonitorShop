package eu.sanjin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sanjin.config.EnvironmentConfig;
import eu.sanjin.config.ProxyConfig;
import eu.sanjin.error.HttpConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.message.BasicHeader;

import java.io.IOException;
import java.util.List;

@Slf4j
public abstract class BaseApiClient {

    protected static final String BACKEND_URL = EnvironmentConfig.getInstance().apiUrl();
    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected String send(ClassicHttpRequest request) {
        try (final var client = getHttpClient()) {
            return client.execute(request, new BasicHttpClientResponseHandler());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new HttpConnectionException(e);
        }
    }

    protected CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
            .setConnectionManager(ProxyConfig.getInstance().getSocksProxyConnectionManager())
            .setDefaultHeaders(List.of(
                //new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + OAuthService.getAuthToken()),
                new BasicHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON)
            ))
            .evictExpiredConnections()
            .build();
    }

}
