package eu.sanjin.test.util.container;

import com.github.terma.javaniotcpproxy.StaticTcpProxyConfig;
import com.github.terma.javaniotcpproxy.TcpProxy;
import eu.sanjin.config.EnvironmentConfig;
import eu.sanjin.test.util.container.model.RecordingTestDescription;
import eu.sanjin.test.util.container.options.ChromeConfig;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.VncRecordingContainer;
import org.testng.ITestResult;

import java.io.File;
import java.util.Optional;

@Slf4j
@SuppressWarnings("resource")
public class SeleniumTestContainer extends BrowserWebDriverContainer<SeleniumTestContainer> {

    private final static String RECORDING_PATH = "./target/recordings";
    private static volatile SeleniumTestContainer container;

    private SeleniumTestContainer() {
        var config = new ChromeConfig();

        withCapabilities(config.getChromeOptions());
        withClasspathResourceMapping("container", "/etc/resource", BindMode.READ_ONLY);

        // Record test for local testing
        setUpRecording();

        Testcontainers.exposeHostPorts(80, 443, 1234, 1235, 5000);
    }

    private void setUpRecording() {
        var recordingDirectory = new File(RECORDING_PATH);
        if (!recordingDirectory.exists()) {
            if (!recordingDirectory.mkdirs()) {
                log.error("Creating folder {} thrown an error. Please check directory permissions.", RECORDING_PATH);
            }
        }
        withRecordingMode(VncRecordingMode.RECORD_ALL, recordingDirectory, VncRecordingContainer.VncRecordingFormat.MP4);
    }

    public static SeleniumTestContainer getInstance() {
        if (container == null) {
            synchronized (SeleniumTestContainer.class) {
                if (container == null) {
                    container = new SeleniumTestContainer();
                }
            }
        }

        return container;
    }

    @Override
    public void start() {
        super.start();

        new TcpProxy(new StaticTcpProxyConfig(
            5900,
            container.getHost(),
            container.getMappedPort(5900),
            1
        )).start();

        log.info("Running VNC proxy on vnc://localhost:5900, password: 'secret'");
    }

    @Override
    public void stop() {
        // Do nothing, let JVM handle stop
    }

    public void saveRecording(ITestResult result) {
        afterTest(new RecordingTestDescription(result), Optional.ofNullable(result.getThrowable()));
    }

    public void fetchRootPage() {
        getWebDriver().get(EnvironmentConfig.getInstance().frontendUrl());
    }

}
