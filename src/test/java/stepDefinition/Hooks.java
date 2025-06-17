package stepDefinition;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import step.Helper;

public class Hooks {

    private Helper helper;
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setup(Scenario scenario) {
        RestAssured.baseURI = helper.getBaseUri();
        logger.info("Base URL from Hooks: {}", RestAssured.baseURI);
        logger.info("Executing scenario: {}", scenario.getName());

    }
}
