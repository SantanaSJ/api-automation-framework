package step;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.model.util.EnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ApiService {

    private EnvironmentVariables environmentVariables;
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    public void sendPostRequest(String endpoint, Object json, String token) {
        Response response = SerenityRest
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(json)
                .when()
                .post(endpoint);
        logger.info("Status code from POST request: {}", response.getStatusCode());
    }

    public void sendPostRequest(String endpoint, Object json, String token, String tenantId) {
        Response response = SerenityRest
                .given()
                .header("Authorization", "Bearer " + token)
                .header("tenantId", tenantId)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(json)
                .when()
                .post(endpoint);
        logger.info("Status code from POST request: {}", response.getStatusCode());
    }

    public void getRequest(String tenantId, String endpoint, String token, String paginationQueryString) {
        Response response = SerenityRest
                .given()
                .header("Authorization", "Bearer " + token)
                .header("tenantId", tenantId)
                .contentType("application/json")
                .when()
                .get(endpoint+paginationQueryString);
        logger.info("Status code from GET request: {}", response.getStatusCode());
    }

    public void getRequest(String endpoint, String token, String paginationQueryString) {
        Response response = SerenityRest
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .when()
                .get(endpoint+paginationQueryString);
        logger.info("Status code from GET request: {}", response.getStatusCode());
    }

    public String getScheduleId(String scheduleName) {
        logger.info("Getting schedule id...");
        List<Map<String, Object>> payload = SerenityRest.lastResponse().getBody().jsonPath().getList("payload");
        String id = "";
        Optional<Map<String, Object>> scheduleMap = payload
                .stream()
                .filter(s -> scheduleName.equals(s.get("name")))
                .findFirst();
        if (scheduleMap.isPresent()) {
            id = (String) scheduleMap.get().get("id");
        } else {
            throw new IllegalStateException("Schedule map not found for schedule name: " + scheduleName);
        }
        logger.info("Schedule id: {}", id);
        return id;
    }
}
