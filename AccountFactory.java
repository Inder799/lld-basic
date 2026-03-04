import java.math.BigDecimal;

public class AccountFactory {

    public static BankAccount createAccount(AccountType type, String accountNumber, String holderName, BigDecimal initialAmount, TransactionRecorder recorder, BigDecimal value) {

        switch (type) {
            case SAVINGS:
                return new SavingsAccount(accountNumber, holderName, initialAmount, recorder, value);
            case CURRENT:
                return new CurrentAccount(accountNumber, holderName, initialAmount, recorder, value);
            default:
                throw new IllegalArgumentException("Invalid Argument Type");
        }

    }
}
