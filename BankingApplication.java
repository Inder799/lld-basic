import java.math.BigDecimal;

public class BankingApplication {
    public static void main(String[] args) {
        TransactionRecorder recorder = new InMemoryTransactionRecorder();

        BankAccount savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS, "1234567890", "John doe", BigDecimal.valueOf(10000), recorder, BigDecimal.valueOf(0.1));
        BankAccount currentAccount = AccountFactory.createAccount(AccountType.CURRENT, "1234567890", "John doe", BigDecimal.valueOf(10000), recorder, BigDecimal.valueOf(5000));
//        InterestBearing savingsAccount = new SavingsAccount("1234567890", "John doe", BigDecimal.valueOf(10000), recorder, BigDecimal.valueOf(0.1));
//        BankAccount currentAccount = new CurrentAccount("1234567890", "John doe", BigDecimal.valueOf(10000), recorder, BigDecimal.valueOf(5000));
        System.out.println(currentAccount.getBalance());
        if(savingsAccount instanceof InterestBearing ib)
            System.out.println(ib.calculateInterest());

    }
}
