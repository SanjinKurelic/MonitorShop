function FindProxyForURL(url, host) {
  if (dnsDomainIs(host, "secret-api.example.org")) {
    return "SOCKS5 host.testcontainers.internal:1234";
  }
  else if (dnsDomainIs(host, "public-api.example.org")) {
    return "PROXY host.testcontainers.internal:1235";
  }
  return "DIRECT";
}
