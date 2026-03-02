import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTransactionRecorder implements TransactionRecorder {

    private final Map<String, List<Transaction>> storage = new HashMap<>();

    @Override
    public void record(BankAccount account, BigDecimal amount) {
        storage
                .computeIfAbsent(account.getAccountNumber(), k -> new ArrayList<>())
                .add(new Transaction(amount));
    }

    public List<Transaction> getAllTransaction(String accountNumber) {
        return storage.getOrDefault(accountNumber, List.of());
    }
}
