package stepDefinition;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;

public class Helper {

    private EnvironmentVariables environmentVariables;

    public String getBaseUri() {
        return getProperty("api.baseurl");
    }

    private String getProperty(String key) {

        String value = System.getProperty(key);

        if (value != null) {
            return value;
        }

        value = System.getenv(key);

        if (value != null) {
            return value;
        }
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(key);
    }
}
