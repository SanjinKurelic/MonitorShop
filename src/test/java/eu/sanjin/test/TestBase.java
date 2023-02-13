package eu.sanjin.test;

import eu.sanjin.api.WiremockClient;
import eu.sanjin.client.step.AddToCartSteps;
import eu.sanjin.jira.service.JiraService;
import eu.sanjin.proxy.ProxyAutoConfigurationServer;
import eu.sanjin.test.util.container.SeleniumTestContainer;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

@Slf4j
public class TestBase {

    protected static final SeleniumTestContainer seleniumContainer = SeleniumTestContainer.getInstance();
    protected AddToCartSteps clientSteps;

    @BeforeClass
    public void setup() {
        // Start proxy server for local development
        ProxyAutoConfigurationServer.startPacServer();

        // Set WireMock state
        new WiremockClient().changeState(this.getClass().getSimpleName());

        // Start selenium test container
        seleniumContainer.start();

        // Define new client steps
        clientSteps = new AddToCartSteps(seleniumContainer.getWebDriver());

        // Navigate to target page
        seleniumContainer.fetchRootPage();
    }

    @AfterClass
    public void tearDown() {
        log.info("Stopping the container");
        seleniumContainer.stop();
    }

    @BeforeMethod
    public void startTest() {
        log.info("Test {} has started", this.getClass().getSimpleName());
    }

    @AfterMethod
    public void endTest(ITestResult result) {
        log.info("Test {} has ended.", this.getClass().getSimpleName());

        // Save the recording
        seleniumContainer.saveRecording(result);

        // Send result to Jira
        new JiraService().sendResultsToJira(result);
    }

}
