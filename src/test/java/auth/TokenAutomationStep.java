package auth;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import step.Helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenAutomationStep {

    @Steps
    private Helper helper;
    @Steps
    private SeleniumTokenAutomation tokenAutomation;
    private static final Logger logger = LoggerFactory.getLogger(TokenAutomationStep.class);

    @Step("Open Welcome page")
    public void openWelcomePage(String url) {
        String baseUrl = helper.getBaseUri();
        tokenAutomation.openWelcomePage(url);
    }

    @Step("Click log in button")
    public void clickLoginButton() {
        tokenAutomation.clickLoginButton();
    }

    @Step("Type username")
    public void typeUsername(String user) {
        String username = helper.getProperty(user);
        tokenAutomation.typeUsername(username);
    }

    @Step("Type password")
    public void typePassword(String user) {
        String password = helper.getProperty(user);
        tokenAutomation.typePassword(password);
    }

    @Step("Click sign in button")
    public void clickSignInButton() {
        tokenAutomation.clickSignInButton();
    }

    @Step("Get token")
    public String getToken() {
        String token = tokenAutomation.getToken();
        return token;
    }

    @Step("Validate authorization token")
    public void tokenNotNull() {
        String token = Serenity.getCurrentSession().get("token").toString();
        assertNotNull(token, "Token should not be null");
    }

    @Step("Set token")
    public void setToken(String token) {
        logger.info("Setting token...");
        Serenity.setSessionVariable("token").to(token);
    }

    public void waitForEventsTitleToAppear() {
        tokenAutomation.waitFoTitle();
    }



}
