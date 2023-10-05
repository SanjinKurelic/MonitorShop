package eu.sanjin.test.util;

import eu.sanjin.test.util.container.options.ChromeConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
public class OldWebDriverConfiguration {

    public static WebDriver createChromeBrowser() {
        // Do not use this approach
        var driverPathChrome = "";
        var chromeAppPath = "";

        switch (System.getProperty("os.name")) {
            case "Linux" -> {
                driverPathChrome = "driver/chromedriver_linux";
                chromeAppPath = "/usr/bin/google-chrome-stable";
            }
            case "Windows 10", "Windows 11" -> {
                driverPathChrome = "driver/chromedriver.exe";
                chromeAppPath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
            }
            case "Mac OS X" -> {
                driverPathChrome = "/opt/homebrew/bin/chromedriver";
                chromeAppPath = "/Applications/Google\\ Chrome.app";
            }
            default -> log.error(System.getProperty("os.name") + " is not supported");
        }

        var chromeOptions = new ChromeConfig().getChromeOptions();
        chromeOptions.setBinary(chromeAppPath);
        System.setProperty("webdriver.chrome.driver", driverPathChrome);

        return new ChromeDriver();
    }

}
