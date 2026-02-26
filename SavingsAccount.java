import java.math.BigDecimal;

public class SavingsAccount extends BankAccount{

    public SavingsAccount(String accountNumber, String holderName, BigDecimal initialAmount) {
        super(accountNumber, holderName, initialAmount);
    }

    @Override
    protected boolean canWithdrawAmount(BigDecimal amount) {
        return getBalance().compareTo(amount) >= 0;
    }
}
