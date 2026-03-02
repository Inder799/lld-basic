import java.math.BigDecimal;

public interface TransactionRecorder {
    void record(BankAccount account, BigDecimal amount);
}
