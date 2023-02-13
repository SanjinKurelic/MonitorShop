package eu.sanjin.proxy;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

@RequiredArgsConstructor
public class Socks5PlainProxyFactory extends PlainConnectionSocketFactory {

    private final Proxy proxy;

    @Override
    public Socket createSocket(final HttpContext context) {
        return new Socket(proxy);
    }

    @Override
    public Socket connectSocket(Socket socket, HttpHost host, InetSocketAddress remoteAddress, InetSocketAddress localAddress, Timeout connectTimeout, Object attachment, HttpContext context) throws IOException {
        var unresolvedRemote = InetSocketAddress.createUnresolved(host.getHostName(), remoteAddress.getPort());
        return super.connectSocket(socket, host, unresolvedRemote, localAddress, connectTimeout, attachment, context);
    }
}
