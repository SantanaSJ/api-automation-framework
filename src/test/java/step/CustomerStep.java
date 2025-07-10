package step;

import actor.Customer;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerStep {

    @Steps
    private ApiService apiService;
    @Steps
    private Helper helper;
    private static final Logger logger = LoggerFactory.getLogger(CustomerStep.class);

    @Step("Create customer")
    public Customer createCustomer(List<Map<String, String>> customerData) {
        logger.info("Creating customer...");
        Customer customerObject = new Customer(customerData.get(0));
        return customerObject;
    }

    @Step("Validate correct payload")
    public void validateCorrectPayload() {
        String id = lastResponse().jsonPath().getString("payload.id");
        String customerId = lastResponse().jsonPath().getString("payload.customer_id");
        List<String> webUsernames = lastResponse().jsonPath().getList("payload.webUsernames");
        String companyName = lastResponse().jsonPath().getString("payload.companyName");

        if (!webUsernames.isEmpty()) {
            for (String webUsername : webUsernames) {
                assertAll("Assert customer emails",
                        () -> assertNotNull(webUsername, "Expected a valid customer name but got null"),
                        () -> assertFalse(webUsername.isEmpty(), "Expected a valid webUsername but got empty string")
                );
            }
        }
        assertAll("Assert valid Customer payload",
                () -> assertNotNull(id, "Expected a valid id but got null"),
                () -> assertNotNull(customerId, "Expected a valid customer id but got null"),
                () -> assertFalse(customerId.isEmpty(), "Expected a valid customer id but got empty string"),
                () -> assertNotNull(companyName, "Expected a valid customerName but got null"),
                () -> assertFalse(companyName.isEmpty(), "Expected a valid companyName but got empty string"));
    }

    @Step("Retrieve all customers")
    public void retrieveAllCustomers(String endpointKey) {
        String endpoint = helper.getProperty(endpointKey);
        String token = Serenity.getCurrentSession().get("token").toString();
        String paginationQueryString = helper.getPaginationQueryString(5, 1);
        logger.info("Sending GET request to endpoint {}{}", endpoint, paginationQueryString);
        apiService.getRequest(endpoint, token, paginationQueryString);
        restAssuredThat(lastResponse -> lastResponse.statusCode(200));
    }
}
