package actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Partner {

    private String name = "";
    private Contact contact;
    private String scheduleId = "";
    private List<PartnerHeader> partnerHeaders;
    private boolean isAlertEnabled = false;

    public Partner(Map<String, String> data, String id) {
        this.scheduleId = id;

        if (data.get("name") != null) {
            this.name = data.get("name");
        }

        if (data.get("isAlertEnabled") != null) {
            this.isAlertEnabled = Boolean.valueOf(data.get("isAlertEnabled"));
        }

        if (data.get("contactName") != null) {
            this.contact = new Contact();
            this.contact.setName(data.get("contactName"));
        }

        if(data.get("emails") != null) {
            this.contact.setEmails(Arrays.stream(data.get("emails").split(",")).map(String::trim).collect(Collectors.toList()));
        }

        this.partnerHeaders = new ArrayList<>();
        int index = 1;
        while (data.containsKey("partnerHeader" + index)) {
            PartnerHeader partnerHeader = new PartnerHeader();
            partnerHeader.setKey(data.get("headerKey" + index));
            partnerHeader.setValue(data.get("headerValue" + index));
            String isSecret = data.get("isSecret" + index);

            if (isSecret != null) {
                partnerHeader.setIsSecret(Boolean.parseBoolean(isSecret));
            }
            this.partnerHeaders.add(partnerHeader);
            index++;
        }
    }
}
