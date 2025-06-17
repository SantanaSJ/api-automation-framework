package stepDefinition;

import actor.Customer;
import actor.Partner;
import auth.TokenAutomationStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import step.*;

import java.util.List;
import java.util.Map;

public class ApiStepDefinitions {

    @Steps
    private CustomerStep customerStep;
    @Steps
    private ApiCommonStep apiCommonSteps;
    @Steps
    private TokenAutomationStep tokenAutomationStep;
    @Steps
    private ScheduleStep scheduleStep;
    @Steps
    PartnerStep partnerStep;
    @Steps
    private Helper helper;
    private static final Logger logger = LoggerFactory.getLogger(ApiStepDefinitions.class);


    @Given("admin is logged in to {string} with {string} and password {string} and the token is stored" )
    public void adminIsLoggedInAndTokenStored(String urlKey, String adminKey, String passKey) {
        if (!Serenity.getCurrentSession().containsKey("token")) {
            logger.info("Logging in as admin");
            tokenAutomationStep.openWelcomePage(urlKey);
            tokenAutomationStep.clickLoginButton();
            tokenAutomationStep.typeUsername(adminKey);
            tokenAutomationStep.typePassword(passKey);
            tokenAutomationStep.clickLoginButton();
            tokenAutomationStep.waitForEventsTitleToAppear();
            String token = tokenAutomationStep.getToken();
            tokenAutomationStep.setToken(token);
        } else {
            logger.info("Token already set, skipping login");
        }
    }


    @And("token should not be null")
    public void tokenShouldNotBeNull() {
        tokenAutomationStep.tokenNotNull();
    }

    @And("{string} is retrieved")
    public void scheduleIdIsRetrieved(String scheduleName) {
        scheduleStep.retrieveAllSchedules();
        scheduleStep.getScheduleId(scheduleName);
    }

    @When("a request is sent to partner {string} with the following data:")
    public void createValidPartner(String endpoint, List<Map<String, String>> data) {
        Partner partner = partnerStep.createPartner(data);
        String tenantId = helper.getProperty("admin.user.tenantId");
        apiCommonSteps.sendPostRequest(endpoint, partner, tenantId);
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        apiCommonSteps.validateStatusCode(expectedStatusCode);
    }

    @And("the response status message should be successful")
    public void statusMessageShouldBeSuccessful() {
        apiCommonSteps.validateSuccessfulMessage();
    }

    @And("the response should contain valid partner payload")
    public void responseContainsValidPartnerPayload() {
        partnerStep.validateCorrectPayload();
    }

    @Given("user is logged in to {string} with username {string} and password {string} and the token is stored")
    public void userIsLoggedInAndTokenIsStored(String urlKey, String usernameKey, String passKey) {
        if (!Serenity.getCurrentSession().containsKey("token")) {
            logger.info("Logging in as admin");
            tokenAutomationStep.openWelcomePage(urlKey);
            tokenAutomationStep.clickLoginButton();
            tokenAutomationStep.typeUsername(usernameKey);
            tokenAutomationStep.typePassword(passKey);
            tokenAutomationStep.clickLoginButton();
            tokenAutomationStep.waitForEventsTitleToAppear();
            String token = tokenAutomationStep.getToken();
            tokenAutomationStep.setToken(token);
        } else {
            logger.info("Token already set, skipping login");
        }
    }

    @When("a request is sent to {string} with the following data:")
    public void createValidCustomer(String endpoint, List<Map<String, String>> data) {
        Customer customer = customerStep.createCustomer(data);
        apiCommonSteps.sendPostRequest(endpoint, customer);
    }

    @And("the response should contain valid customer payload")
    public void theResponseShouldContainValidCustomerPayload() {
        customerStep.validateCorrectPayload();
    }

    @When("a request is sent to {string}")
    public void getAllCustomers(String endpoint) {
        customerStep.retrieveAllCustomers(endpoint);
    }
}
