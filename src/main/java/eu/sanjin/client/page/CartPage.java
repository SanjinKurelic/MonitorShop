package eu.sanjin.client.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public non-sealed class CartPage extends BasePage {

    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);

        this.driver = driver;
    }

    public void changeQuantity(int index, int quantity) {
        var quantityInputList = driver.findElements(By.cssSelector("input.price"));
        var quantityInput = quantityInputList.get(index);

        setText(quantityInput, String.valueOf(quantity));
    }

    public double getTotal() {
        var totalContainerClassName = By.className("total-label");

        requireElementVisible(totalContainerClassName, 10);

        var totalContainer = getText(totalContainerClassName).split("€");

        // Will throw exception if number not found
        return Double.parseDouble(totalContainer[1]);
    }
}
