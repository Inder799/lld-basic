import java.math.BigDecimal;
import java.util.concurrent.TransferQueue;

public class CurrentAccount extends BankAccount{
    private final BigDecimal overdraftLimit;
    public CurrentAccount(String accountNumber, String holderName, BigDecimal initialAmount, TransactionRecorder recorder, BigDecimal overdraftLimit) {
        super(accountNumber, holderName, initialAmount, recorder);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    protected boolean canWithdrawAmount(BigDecimal amount) {
        BigDecimal allowed = getBalance().add(overdraftLimit);
        return allowed.compareTo(amount) >= 0;
    }
}
