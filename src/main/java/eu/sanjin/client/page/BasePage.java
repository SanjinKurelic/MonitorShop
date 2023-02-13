package eu.sanjin.client.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@SuppressWarnings("SameParameterValue")
public sealed class BasePage permits CartPage, HomePage {

    private final WebDriver driver;

    protected void setText(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    protected String getText(By by) {
        return Optional.ofNullable(driver.findElement(by).getText())
            .map(String::trim)
            .orElse("");
    }

    protected void requireElementVisible(By element, int numberOfSeconds) {
        waitFor(ExpectedConditions.visibilityOfElementLocated(element), numberOfSeconds);
    }

    protected void requireElementClickable(WebElement element, int numberOfSeconds) {
        waitFor(ExpectedConditions.elementToBeClickable(element), numberOfSeconds);
    }

    @SuppressWarnings("unused")
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    private void waitFor(ExpectedCondition<WebElement> expectedCondition, int numberOfSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(numberOfSeconds)).until(expectedCondition);
    }
}
