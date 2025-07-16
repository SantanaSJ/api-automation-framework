package actor;

import java.util.ArrayList;
import java.util.List;

public class Contact {

    private String name = "";
    private List<String> emails = new ArrayList<>();

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getEmails() {
        return emails;
    }

    public Contact setEmails(List<String> emails) {
        this.emails = emails;
        return this;
    }
}
