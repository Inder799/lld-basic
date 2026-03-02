import java.math.BigDecimal;

public class BankingApplication {
    public static void main(String[] args) {
        TransactionRecorder recorder = new InMemoryTransactionRecorder();

        InterestBearing savingsAccount = new SavingsAccount("1234567890", "John doe", BigDecimal.valueOf(10000), recorder, BigDecimal.valueOf(0.1));
        BankAccount currentAccount = new CurrentAccount("1234567890", "John doe", BigDecimal.valueOf(10000), recorder, BigDecimal.valueOf(5000));
        System.out.println(currentAccount.getBalance());
        System.out.println(savingsAccount.calculateInterest());

    }
}
