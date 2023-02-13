package eu.sanjin.client.step;

import eu.sanjin.client.page.CartPage;
import eu.sanjin.client.page.HomePage;
import org.openqa.selenium.WebDriver;

public class AddToCartSteps {

    private final HomePage homePage;
    private final CartPage cartPage;

    public AddToCartSteps(WebDriver driver) {
        this.homePage = new HomePage(driver);
        this.cartPage = new CartPage(driver);
    }

    public void addItemToCart(int index) {
        homePage.addProductToCart(index);
    }

    public void redirectToCart() {
        homePage.redirectToCart();
    }

    @SuppressWarnings("unused")
    public void changeQuantity(int index, int quantity) {
        cartPage.changeQuantity(index, quantity);
    }

    public double getCartTotal() {
        return cartPage.getTotal();
    }
}
