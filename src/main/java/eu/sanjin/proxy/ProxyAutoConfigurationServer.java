package eu.sanjin.proxy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import eu.sanjin.config.ProxyConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;

/**
 * Simple file server that serves PAC file for Chrome for local development.
 * Chrome does not support PAC argument definition from file nor data URL.
 * This class can be replaced with SimpleFileServer (Java 18+) in near future.
 */
@Slf4j
public class ProxyAutoConfigurationServer implements HttpHandler {

    private static final int SERVER_PORT = 8977;
    private static final String SERVER_DIRECTORY = "static";
    private static HttpServer instance;

    public static void startPacServer() {
        // Do not start PAC server on Jenkins
        if (Boolean.FALSE.equals(ProxyConfig.getInstance().proxyEnabled())) {
            return;
        }
        // If server is not started, start it
        if (Objects.isNull(instance)) {
            try {
                var server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
                server.createContext("/", new ProxyAutoConfigurationServer());
                server.setExecutor(Executors.newSingleThreadExecutor());
                server.start();

                instance = server;
                log.info("PAC server started on port {}", SERVER_PORT);
            }
            catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var fileName = SERVER_DIRECTORY + exchange.getRequestURI().getPath();

        var file = new File(
            Optional.ofNullable(getClass().getClassLoader().getResource(fileName))
                .map(URL::getFile)
                .orElse("undefined")
        );

        if (!file.exists()) {
            // Http status 404, without content (-1)
            exchange.sendResponseHeaders(404, -1);
            log.warn("Filename {} not found on PAC server", fileName);
            return;
        }

        var response = Files.readString(file.toPath());

        exchange.sendResponseHeaders(200, response.length());

        var out = exchange.getResponseBody();
        out.write(response.getBytes());
        out.flush();
        out.close();
    }
}
