package actor;


public class PartnerHeader {

    private String key = "";
    private String value = "";
    private Boolean isSecret = false;

    public String getKey() {
        return key;
    }

    public PartnerHeader setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public PartnerHeader setValue(String value) {
        this.value = value;
        return this;
    }

    public Boolean getSecret() {
        return isSecret;
    }

    public PartnerHeader setIsSecret(Boolean secret) {
        isSecret = secret;
        return this;
    }
}
