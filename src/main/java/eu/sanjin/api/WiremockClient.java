package eu.sanjin.api;

import eu.sanjin.error.ResponseParseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class WiremockClient extends BaseApiClient {

    private static final String WIREMOCK_ENDPOINT = BACKEND_URL + "/wiremock/state";

    public void changeState(String state) {
        log.info("Change state for wiremock requests to " + state);

        try {
            var request = new HttpPut(WIREMOCK_ENDPOINT);

            request.setEntity(new StringEntity(objectMapper.writeValueAsString(Map.of("state", state)),
                ContentType.APPLICATION_JSON));

            //send(request);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ResponseParseException(e);
        }
    }

}
