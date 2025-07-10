package actor;

import java.util.*;
import java.util.stream.Collectors;

public class Customer {
    private String customerId = "";
    private List<String> usernames = new ArrayList<>();
    private String companyName = "";

    public Customer (Map<String, String> map) {
        this.customerId = getShuffledId();

        if (map.get("usernames") != null) {
            this.usernames =
                    Arrays
                            .stream(map.get("userbanes").split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
        }

        if (map.get("companyName") != null) {
            this.companyName = map.get("companyName");
        }
    }

    private String getShuffledId() {
        List<String> numbers = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            numbers.add(String.valueOf(i));
        }

        Collections.shuffle(numbers);

        StringBuilder ids = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            ids.append(numbers.get(i));
        }
        return ids.toString();
    }

    public String getCustomerId() {
        return customerId;
    }

    public Customer setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public Customer setUsernames(List<String> usernames) {
        this.usernames = usernames;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Customer setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }
}
