package step;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;


public class ApiCommonSteps {

    @Steps
    private Helper helper;
    @Steps
    private ApiService apiService;
    private static final Logger logger = LoggerFactory.getLogger(ApiCommonSteps.class);

    @Step("Validate status code")
    public void validateStatusCode(int expectedStatusCode) {
        restAssuredThat(lastResponse -> lastResponse.statusCode(expectedStatusCode));
        logger.info("Validating status code: {}", SerenityRest.lastResponse().getStatusCode());
    }

    @Step("Validate successful status message")
    public void validateSuccessfulMessage() {
        boolean isSuccess = lastResponse().jsonPath().getBoolean("status.isSuccess");
        assertThat("Expected status: true, but was false", isSuccess, is(true));
        logger.info("Validating status message: {}", isSuccess);
    }

    @Step("Send POST request as a user")
    public void sendPostRequest(String endpointKey, Object body) {
        String endpoint = helper.getProperty(endpointKey);
        logger.info("Sending POST request to endpoint: {}", endpoint);
        String token = Serenity.getCurrentSession().get("token").toString();

        apiService.sendPostRequest(endpoint, body, token);
    }

    @Step("Send POST request as tenant admin")
    public void sendPostRequest(String endpointKey, Object body, String tenantId) {
        String endpoint = helper.getProperty(endpointKey);
        logger.info("Sending POST request to endpoint: {}", endpoint);
        String token = Serenity.getCurrentSession().get("token").toString();

        apiService.sendPostRequest(endpoint, body, token, tenantId);
    }

}
