package step;

import actor.Partner;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PartnerStep {

    @Steps
    private Helper helper;
    @Steps
    private ApiService apiService;
    private static final Logger logger = LoggerFactory.getLogger(PartnerStep.class);

    @Step("Create partner")
    public Partner createPartner(List<Map<String, String>> table) {
        logger.info("Creating partner...");
        String scheduleId = (String) Serenity.getCurrentSession().get("scheduleId");
        Partner partner = new Partner(table.get(0), scheduleId);
        return partner;
    }

    @Step("validate correct payload")
    public void validateCorrectPayload() {
        logger.info("Validating correct payload...");

        String partnerId = lastResponse().jsonPath().getString("payload.id");
        String partnerName = lastResponse().jsonPath().getString("payload.name");
        String contactId = lastResponse().jsonPath().getString("payload.contact.id");
        String contactName = lastResponse().jsonPath().getString("payload.contact.name");
        List<String> emails = lastResponse().jsonPath().getList("payload.contact.emails");
        String scheduleName = lastResponse().jsonPath().getString("payload.schedule.name");
        String scheduleId = lastResponse().jsonPath().getString("payload.schedule.id");
        List<Map<String, Object>> partnerHeaders = lastResponse().jsonPath().getList("payload.partnerHeaders");
        String isAlertEnabled = lastResponse().jsonPath().getString("payload.iAlertEnabled");

        for (Map<String, Object> partnerHeader : partnerHeaders) {
            String id = (String) partnerHeader.get("id");
            String key = (String) partnerHeader.get("key");
            String value = (String) partnerHeader.get("value");

            assertAll("Assert al valid header values",
                    () -> assertNotNull(id),
                    () -> assertNotNull(key),
                    () -> assertNotNull(value),
                    () -> assertFalse(key.isEmpty()),
                    () -> assertFalse(value.isEmpty())
            );
        }

        assertAll("Assert valid partner payload",
                () -> assertNotNull(partnerId),
                () -> assertFalse(partnerId.isEmpty()),
                () -> assertNotNull(partnerName),
                () -> assertFalse(partnerName.isEmpty()),
                () -> assertNotNull(contactId),
                () -> assertFalse(contactId.isEmpty()),
                () -> assertNotNull(contactName),
                () -> assertFalse(contactName.isEmpty()),
                () -> assertThat(emails.size(), is(greaterThan(0))),
                () -> assertNotNull(scheduleName),
                () -> assertFalse(scheduleName.isEmpty()),
                () -> assertNotNull(scheduleId),
                () -> assertFalse(scheduleId.isEmpty()),
                () -> assertNotNull(isAlertEnabled));
    }
}
