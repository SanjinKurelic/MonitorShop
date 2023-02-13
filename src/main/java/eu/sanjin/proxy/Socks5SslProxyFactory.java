package eu.sanjin.proxy;

import eu.sanjin.config.SslContextConfig;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class Socks5SslProxyFactory extends SSLConnectionSocketFactory {

    private final Proxy proxy;

    public Socks5SslProxyFactory(Proxy proxy) {
        super(SslContextConfig.getInstance().getSslContext());
        this.proxy = proxy;
    }

    @Override
    public Socket createSocket(HttpContext context) {
        return new Socket(proxy);
    }

    @Override
    public Socket connectSocket(Socket socket, HttpHost host, InetSocketAddress remoteAddress, InetSocketAddress localAddress, Timeout connectTimeout, Object attachment, HttpContext context) throws IOException {
        var unresolvedRemote = InetSocketAddress.createUnresolved(host.getHostName(), remoteAddress.getPort());
        return super.connectSocket(socket, host, unresolvedRemote, localAddress, connectTimeout, attachment, context);
    }
}
