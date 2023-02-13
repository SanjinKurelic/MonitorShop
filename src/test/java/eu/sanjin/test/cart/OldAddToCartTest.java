package eu.sanjin.test.cart;

import eu.sanjin.test.OldTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OldAddToCartTest extends OldTestBase {

    @Test(testName = "add-to-cart-OLD")
    public void addItemToCartTest() {
        // Add first item to cart
        clientSteps.addItemToCart(0);

        // Add another first item to cart
        clientSteps.addItemToCart(0);

        // Add second item to cart
        clientSteps.addItemToCart(1);

        // Go to cart page
        clientSteps.redirectToCart();

        // Check if total is correct
        Assert.assertEquals(clientSteps.getCartTotal(), 1000);
    }
}
