package auth;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stepDefinition.Hooks;

import java.time.Duration;
import java.util.List;

import static net.serenitybdd.core.Serenity.getDriver;
import static org.junit.Assert.assertTrue;

public class SeleniumTokenAutomation {

    private final By loginButtonLocator = By.xpath("//button[contains(text(),' Log in')]");
    private final By usernameFieldLocator = By.id("signInFormUsername");
    private final By passwordFieldLocator = By.id("signInFormPassword");
    private final By submitLocator = By.name("signInSubmitButton");
    private final By evensTitleLocator = By.xpath("//button[contains(text(),' Log in')]");
    private static final Logger logger = LoggerFactory.getLogger(SeleniumTokenAutomation.class);

    public void openWelcomePage(String url) {
        getDriver().get(url);
    }

    public void clickLoginButton() {
        WebElement loginButtonElement = getDriver().findElement(loginButtonLocator);
        loginButtonElement.click();
    }

    public void typeUsername(String testUser) {
        List<WebElement> usernameElements = getDriver().findElements(usernameFieldLocator);
        usernameElements.get(1).sendKeys(testUser);
    }
    public void typePassword(String pass) {
        List<WebElement> passwordElements = getDriver().findElements(passwordFieldLocator);
        passwordElements.get(1).sendKeys(pass);
    }

    public void clickSignInButton() {
        List<WebElement> submitButtonElements = getDriver().findElements(submitLocator);
        submitButtonElements.get(1).click();
    }

    public String getToken() {
        logger.info("Getting token...");

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        String token = (String) executor.executeScript("return JSON.parse(localstorage.getItem(\"oidc.user...\")).id_token");
        logger.info("Token: {}", token.substring(10, 20));

        getDriver().quit();
        return token;
    }

    public void waitFoTitle() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        WebElement eventsTitleElement = getDriver().findElement(evensTitleLocator);
        Boolean isTitleDisplayed = wait.until(d -> eventsTitleElement.isDisplayed());
        assertTrue("Events Title not displayed", isTitleDisplayed);
    }

}
