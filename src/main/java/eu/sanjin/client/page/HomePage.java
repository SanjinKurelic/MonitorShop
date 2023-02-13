package eu.sanjin.client.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public non-sealed class HomePage extends BasePage {

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);

        this.driver = driver;
    }

    public void addProductToCart(int index) {
        var products = driver.findElements(By.cssSelector("div.product p.action"));
        var product = products.get(index);

        requireElementClickable(product, 10);

        product.click();
    }

    public void redirectToCart() {
        var links = driver.findElements(By.cssSelector(".topnav a"));

        links.get(links.size() - 1).click();
    }
}
