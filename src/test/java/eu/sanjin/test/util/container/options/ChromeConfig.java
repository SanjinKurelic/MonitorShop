package eu.sanjin.test.util.container.options;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeConfig {

    public ChromeOptions getChromeOptions() {
        var options = new ChromeOptions();

        options.addArguments("start-maximized");

        options.addArguments("--proxy-pac-url=http://host.testcontainers.internal:8977/proxy_config.js");

        options.setCapability("acceptInsecureCerts", true);
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        return options;
    }

}
