package step;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import org.checkerframework.checker.fenum.qual.SwingTextOrientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.junit.Assert.assertNotNull;

public class ScheduleStep {

    @Steps
    private ApiService apiService;
    @Steps
    private Helper helper;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleStep.class);

    @Step("Retrieve all schedules")
    public void retrieveAllSchedules() {
        logger.info("Retrieving all schedules...");
        String tenantId = helper.getProperty("admin.user.tenantId");
        String endpoint = helper.getProperty("api.endpoint.schedules");
        String token = Serenity.getCurrentSession().get("token").toString();
        String paginationQueryString = helper.getPaginationQueryString(5, 1);
        logger.info("Tenant id: {}", tenantId);
        logger.info("Sending GET request to endpoint {}{}", endpoint, paginationQueryString);
        apiService.getRequest(tenantId, endpoint, token, paginationQueryString);
        restAssuredThat(lastResponse -> lastResponse.statusCode(200));
    }

    @Step("Get schedule")
    public void getSchedule(String scheduleName) {
        String scheduleId = apiService.getScheduleId(scheduleName);
        Serenity.setSessionVariable("scheduleId").to(scheduleId);
        assertNotNull("Schedule id should not be null", scheduleId);
    }
}
