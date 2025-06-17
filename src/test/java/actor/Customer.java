package actor;

import java.util.*;
import java.util.stream.Collectors;

public class Customer {

    private String customerId = "";
    private List<String> webUsernames = new ArrayList<String>();
    private String companyName = "";

    public Customer(Map<String, String> data) {
        this.customerId = getShuffledId();

        if (data.get("webUsernames") != null) {
            this.webUsernames = Arrays.stream(data
                    .get("webUsernames")
                    .split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }

        if (data.get("companyName") != null) {
            this.companyName = data.get("companyName");
        }
    }

    private String getShuffledId() {
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            numbers.add(String.valueOf(i));
        }

        Collections.shuffle(numbers);

        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            ids.append(numbers.get(i));
        }
        return ids.toString();
    }

}
