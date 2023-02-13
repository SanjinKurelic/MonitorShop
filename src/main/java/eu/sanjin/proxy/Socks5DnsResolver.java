package eu.sanjin.proxy;

import org.apache.hc.client5.http.DnsResolver;

import java.net.InetAddress;

public class Socks5DnsResolver implements DnsResolver {
    @Override
    public InetAddress[] resolve(String host) {
        // Return some fake DNS record for every request, we won't be using it
        return new InetAddress[]{InetAddress.getLoopbackAddress()};
    }

    @Override
    public String resolveCanonicalHostname(String s) {
        return s;
    }
}
