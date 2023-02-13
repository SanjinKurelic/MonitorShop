package eu.sanjin.config;

import eu.sanjin.error.HttpConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.constretto.annotation.Configure;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

@Slf4j
public record SslContextConfig(String jksPassword) {

    private static SslContextConfig instance;

    @Configure
    public SslContextConfig {
        // This is record constructor sugar used for adding constructor annotations
        log.info("SSL configuration loaded");
    }

    public static SslContextConfig getInstance() {
        if (Objects.isNull(instance)) {
            instance = ConstrettoConfig.getConfiguration().as(SslContextConfig.class);
        }

        return instance;
    }

    public SSLContext getSslContext() {
        try {
            return new SSLContextBuilder()
                .loadTrustMaterial(
                    new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test.jks")).getFile()),
                    jksPassword.toCharArray()
                )
                .build();
        } catch (GeneralSecurityException | IOException | NullPointerException e) {
            log.error(e.getMessage(), e);
            throw new HttpConnectionException(e);
        }
    }

}
