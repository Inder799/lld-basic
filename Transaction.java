import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private BigDecimal amount;
    private LocalDateTime time;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

}
