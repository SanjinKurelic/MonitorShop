package eu.sanjin.test;

import eu.sanjin.api.WiremockClient;
import eu.sanjin.client.step.AddToCartSteps;
import eu.sanjin.config.EnvironmentConfig;
import eu.sanjin.jira.service.JiraService;
import eu.sanjin.proxy.ProxyAutoConfigurationServer;
import eu.sanjin.test.util.OldWebDriverConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

@Slf4j
public class OldTestBase {

    private WebDriver driver;
    protected AddToCartSteps clientSteps;

    @BeforeClass
    public void setup() {
        // Start proxy server for local development
        ProxyAutoConfigurationServer.startPacServer();

        // Set WireMock state
        new WiremockClient().changeState(this.getClass().getSimpleName());

        // Start selenium test container
        driver = OldWebDriverConfiguration.createChromeBrowser();

        // Define new client steps
        clientSteps = new AddToCartSteps(driver);

        // Navigate to target page
        driver.get("http://localhost:5000");
    }

    @AfterClass
    public void tearDown() {
        log.info("Stopping the driver");
        //driver.quit();
    }

    @BeforeMethod
    public void startTest() {
        log.info("Test {} has started", this.getClass().getSimpleName());
    }

    @AfterMethod
    public void endTest(ITestResult result) {
        log.info("Test {} has ended.", this.getClass().getSimpleName());

        // Send result to Jira
        new JiraService().sendResultsToJira(result);
    }
}
