import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private List<BankAccount> accounts;

    public Customer(String name) {
        this.name = name;
        accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }
}
