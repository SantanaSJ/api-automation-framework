package stepDefinition;

import actor.Customer;
import actor.Partner;
import auth.TokenAutomationSteps;
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
    private ApiCommonSteps commonSteps;
    @Steps
    private ScheduleStep scheduleStep;
    @Steps
    private Helper helper;
    @Steps
    private TokenAutomationSteps tokenAutomationSteps;
    @Steps
    private PartnerStep partnerStep;
    private static final Logger logger = LoggerFactory.getLogger(ApiStepDefinitions.class);

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        commonSteps.validateStatusCode(expectedStatusCode);
    }

    @When("a request is sent to {string} with the following data:")
    public void createValidCustomer(String endpoint, List<Map<String, String>> customerData) {
        Customer customer = customerStep.createCustomer(customerData);
        commonSteps.sendPostRequest(endpoint, customer);
    }

    @And("the response status message should be successful")
    public void theStatusMessageShouldBeSuccessful() {
        commonSteps.validateSuccessfulMessage();
    }

    @And("token should not be null")
    public void tokenIsNotNull() {
        tokenAutomationSteps.tokenNotNull();
    }

    @Given("client has retrieved a new token as admin from {string} as user {string} and password {string}")
    public void adminIsLoggedInAndTokenIsStored(String urlKey, String adminKey, String passKey) {
        if (!Serenity.getCurrentSession().containsKey("token")) {
            logger.info("Admin is not logged in yet, logging in...");
            tokenAutomationSteps.openWelcomePage(urlKey);
            tokenAutomationSteps.clickLoginButton();
            tokenAutomationSteps.typeUsername(adminKey);
            tokenAutomationSteps.typePassword(passKey);
            tokenAutomationSteps.clickSignInButton();
            tokenAutomationSteps.waitForEventsTitleToAppear();
            String token = tokenAutomationSteps.getToken();
            logger.info("Admin logged in, token: {}", token);
            tokenAutomationSteps.setToken(token);
        } else {
            logger.info("Admin is already logged in");
        }
    }

    @When("{string} is retrieved")
    public void scheduleIdIsRetrieved(String scheduleName) {
        scheduleStep.retrieveAllSchedules();
        scheduleStep.getSchedule(scheduleName);
    }

    @And("a request is sent to {string} with the following data:")
    public void createValidPartner(String endpoint, List<Map<String, String>> table) {
        Partner partner = partnerStep.createPartner(table);
        String tenantId = helper.getProperty("admin.user.tenantId");
        commonSteps.sendPostRequest(endpoint, partner, tenantId);
    }

    @And("the response should contain valid partner payload")
    public void responseContainsValidPartnerPayload() {
        partnerStep.validateCorrectPayload();
    }

    @And("the response should contain valid customer payload")
    public void responseContainsValidCustomerPayload() {
        customerStep.validateCorrectPayload();
    }

    @Given("user is logged in to {string} with username {string} and password {string} and the token is stored")
    public void userIsLoggedIn(String urlKey, String userKey, String passKey) {
        if (!Serenity.getCurrentSession().containsKey("token")) {
            logger.info("User is not logged in yet, logging in...");
            tokenAutomationSteps.openWelcomePage(urlKey);
            tokenAutomationSteps.clickLoginButton();
            tokenAutomationSteps.typeUsername(userKey);
            tokenAutomationSteps.typePassword(passKey);
            tokenAutomationSteps.clickSignInButton();
            tokenAutomationSteps.waitForEventsTitleToAppear();
            String token = tokenAutomationSteps.getToken();
            logger.info("User logged in, token: {}", token);
            tokenAutomationSteps.setToken(token);
        } else {
            logger.info("User is already logged in");
        }
    }

    @And("the response should contain valid customers payload")
    public void responseContainsValidCustomersPayload(String endpoint) {
        customerStep.retrieveAllCustomers(endpoint);
    }

    @When("a request is sent to {string}")
    public void retrieveAllCustomers(String endpoint) {
        customerStep.retrieveAllCustomers(endpoint);
    }
}
