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
    private List<PartnerHeader> partnerHeaderList;
    private Boolean isAlertEnabled = false;

    public Partner(Map<String, String> map, String id) {
        this.scheduleId = id;

        if (map.get("name") != null) {
            this.name = map.get("name");
        }

        if (map.get("contactName") != null) {

            this.contact = new Contact();
            this.contact.setName(map.get("contactName"));
        }

        if (map.get("isAlertEnabled") != null) {
            this.isAlertEnabled = Boolean.parseBoolean(map.get("isAlertEnabled"));
        }

        if (map.get("email") != null) {
            this.contact.setEmails(Arrays.stream(map.get("emails").split(",")).map(String::trim).collect(Collectors.toList()));
        }

        this.partnerHeaderList = new ArrayList<>();

        int index = 1;

        while (map.containsKey("headerKey" + index)) {
            PartnerHeader partnerHeader = new PartnerHeader();
            partnerHeader.setKey(map.get("headerKey" + index));
            partnerHeader.setValue(map.get("headerValue" + index));
            String isSecret = map.get("isSecret" + index);

            if (isSecret != null) {
                partnerHeader.setIsSecret(Boolean.parseBoolean(isSecret));
            }
            this.partnerHeaderList.add(partnerHeader);
        }
    }

    public String getName() {
        return name;
    }

    public Partner setName(String name) {
        this.name = name;
        return this;
    }

    public Contact getContact() {
        return contact;
    }

    public Partner setContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public Partner setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
        return this;
    }

    public List<PartnerHeader> getPartnerHeaderList() {
        return partnerHeaderList;
    }

    public Partner setPartnerHeaderList(List<PartnerHeader> partnerHeaderList) {
        this.partnerHeaderList = partnerHeaderList;
        return this;
    }

    public Boolean getAlertEnabled() {
        return isAlertEnabled;
    }

    public Partner setAlertEnabled(Boolean alertEnabled) {
        isAlertEnabled = alertEnabled;
        return this;
    }
}
