# Monitor Shop

Monitor Shop is a web shop for buying monitors. This is a demo application for Selenium automated test presentation.

Implemented features are:

- user can add monitor to cart
- user can change quantity of item in cart

## Getting started:

Project has three main parts:

- Frontend which is written in Svelte
- Automatic tests that are written in Java 17
- Test generator which is written in JS using ChatGPT

Running the project will require Java, NPM and Docker installed on the system:

- Java/JDK 17+
- Node.js and NPM
- Docker

## Running

Firstly, frontend should be started, so we can fire test cases against it. To start frontend, open the terminal inside `src/frontend/` directory and run this command:
> npm run dev

After that, you will be able to start TestNG cases with Maven:
> .\mvnw clean test

There are two test cases:
- Selenium with TestContainers (require Docker on host system)
- Selenium with local driver (require Chrome version 110 on host system)

To run AI which will generate automatic test cases, you need to run following commands:

Run this project with:

`node .\generate-selenium-test.js <page_url>`

There are two example pages in folder `examples`. You can run them with following command:

> For login example:
>
> `node .\generate-selenium-test.js login`

> For calculator example:
>
> `node .\generate-selenium-test.js calculator`

On Git repo there are also two files which were generated with this app.

Working example: `5773708522962766.side`

Invalid example: `19752503054579784.side`

### Side example

ChatGPT can also generate Java code for Selenium test cases, example for login page:

```java
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HtmlTest {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Test Case 1: Verify page title
        driver.get("html-supplied-by-user-url");
        String expectedTitle = "Title";
        String actualTitle = driver.getTitle();
        if (expectedTitle.equals(actualTitle)) {
            System.out.println("Page title is correct: " + actualTitle);
        } else {
            System.out.println("Page title is incorrect. Expected: " + expectedTitle + ". Actual: " + actualTitle);
        }

        // Test Case 2: Submit form with blank fields
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        String errorMessage = errorElement.getText();
        if (errorMessage.equals("Please fill the required fields")) {
            System.out.println("Form validation for blank fields is working: " + errorMessage);
        } else {
            System.out.println("Form validation for blank fields is not working. Error message: " + errorMessage);
        }

        // Test Case 3: Submit form with password less than 8 characters
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("testuser");
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("testuser@test.com");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("1234");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        errorMessage = errorElement.getText();
        if (errorMessage.equals("Your password must include at least 8 characters")) {
            System.out.println("Form validation for password less than 8 characters is working: " + errorMessage);
        } else {
            System.out.println("Form validation for password less than 8 characters is not working. Error message: " + errorMessage);
        }

        // Test Case 4: Submit form with valid data
        passwordField.clear();
        passwordField.sendKeys("password123");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        String alertMessage = driver.switchTo().alert().getText();
        if (alertMessage.equals("Successfully logged in")) {
            System.out.println("Form is working with valid data: " + alertMessage);
        } else {
            System.out.println("Form is not working with valid data. Alert message: " + alertMessage);
        }
    }
}
```

## Technologies:

- Selenium
- Selenium Fluent
- Apache Http Client 5
- Constretto
- Lombok
- MapStruct
- TestContainers
- TestNG
- Socks 5 proxy
- Svelte
- ChatGPT
- Axios
- Express
