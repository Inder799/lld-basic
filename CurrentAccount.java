import java.math.BigDecimal;

public class CurrentAccount extends BankAccount{
    private final BigDecimal overdraftLimit;
    public CurrentAccount(String accountNumber, String holderName, BigDecimal initialAmount, BigDecimal overdraftLimit) {
        super(accountNumber, holderName, initialAmount);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    protected boolean canWithdrawAmount(BigDecimal amount) {
        BigDecimal allowed = getBalance().add(overdraftLimit);
        return allowed.compareTo(amount) >= 0;
    }
}
