package eu.sanjin.config;

import eu.sanjin.proxy.Socks5DnsResolver;
import eu.sanjin.proxy.Socks5PlainProxyFactory;
import eu.sanjin.proxy.Socks5SslProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.util.TimeValue;
import org.constretto.annotation.Configure;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;

@Slf4j
public record ProxyConfig(String proxyIp, Integer proxyPort, Boolean proxyEnabled) {

    private static ProxyConfig instance;

    @Configure
    public ProxyConfig {
        // This is record constructor sugar used for adding constructor annotations
        log.info("Proxy configuration loaded");
    }

    public static ProxyConfig getInstance() {
        if (Objects.isNull(instance)) {
            instance = ConstrettoConfig.getConfiguration().as(ProxyConfig.class);
        }

        return instance;
    }

    public HttpClientConnectionManager getSocksProxyConnectionManager() {
        if (Boolean.FALSE.equals(proxyEnabled)) {
            return null;
        }

        var proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyIp, proxyPort));

        var registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register(URIScheme.HTTP.getId(), new Socks5PlainProxyFactory(proxy))
            .register(URIScheme.HTTPS.getId(), new Socks5SslProxyFactory(proxy))
            .build();

        return new PoolingHttpClientConnectionManager(
            registry, PoolConcurrencyPolicy.STRICT, PoolReusePolicy.LIFO, TimeValue.NEG_ONE_MILLISECOND,
            null, new Socks5DnsResolver(), null
        );
    }

}
