package eu.sanjin.api;

import eu.sanjin.error.ResponseParseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;

import java.io.IOException;

@Slf4j
@SuppressWarnings("unused")
public class CheckoutApiClient extends BaseApiClient {

    private static final String CHECKOUT_ENDPOINT = BACKEND_URL + "/checkout";

    public String getOrderId() {
        try {
            var request = new HttpGet(CHECKOUT_ENDPOINT);

            var response = send(request);

            return objectMapper.readTree(response).at("/details/orderId").asText();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ResponseParseException(e);
        }
    }
}

