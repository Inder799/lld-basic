import java.math.BigDecimal;

public class SavingsAccount extends BankAccount implements InterestBearing{

    private final BigDecimal interestRate;

    public SavingsAccount(String accountNumber, String holderName, BigDecimal initialAmount, TransactionRecorder recorder, BigDecimal interestRate) {
        super(accountNumber, holderName, initialAmount, recorder);
        this.interestRate = interestRate;
    }

    @Override
    protected boolean canWithdrawAmount(BigDecimal amount) {
        return getBalance().compareTo(amount) >= 0;
    }

    @Override
    public BigDecimal calculateInterest() {
        return getBalance().multiply(interestRate);
    }

    public void applyInterest() {
        BigDecimal interest = calculateInterest();
        deposit(interest);
    }
}
