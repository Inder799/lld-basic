import java.math.BigDecimal;

enum Status {
    ACTIVE {
        @Override
        public boolean canWithdraw() {
            return true;
        }

        @Override
        public boolean canDeposit() {
            return true;
        }

        @Override
        public boolean canTransitionTo(Status newStatus) {
            return newStatus == FROZEN || newStatus == CLOSED;
        }
    },
    FROZEN {
        @Override
        public boolean canWithdraw() {
            return false;
        }

        @Override
        public boolean canDeposit() {
            return false;
        }

        @Override
        public boolean canTransitionTo(Status newStatus) {
            return newStatus == CLOSED || newStatus == ACTIVE;
        }
    },
    CLOSED {
        @Override
        public boolean canWithdraw() {
            return false;
        }

        @Override
        public boolean canDeposit() {
            return false;
        }

        @Override
        public boolean canTransitionTo(Status newStatus) {
            return false;
        }
    };

    public abstract boolean canWithdraw();

    public abstract boolean canDeposit();

    public abstract boolean canTransitionTo(Status newStatus);
}

abstract class BankAccount {
    private final String accountNumber;
    private final String holderName;
    private BigDecimal balance;
    private Status status;

    public BankAccount(String accountNumber, String holderName, BigDecimal initialAmount) {
        if (initialAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial Amount can't be negative");
        }
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialAmount;
        this.status = Status.ACTIVE;
    }

    public void deposit(BigDecimal amount) {
        if (!status.canDeposit()) {
            throw new IllegalStateException("Account is not ACTIVE");
        }
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public final void withdraw(BigDecimal amount) {
        if (!status.canWithdraw()) {
            throw new IllegalStateException("Account is not ACTIVE");
        }
        validateAmount(amount);

        if(!canWithdrawAmount(amount)) {
            throw new IllegalArgumentException("Withdrawal exceeds allowed limit");
        }
        this.balance = this.balance.subtract(amount);
    }

    protected abstract boolean canWithdrawAmount(BigDecimal amount);

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount can't be negative");
        }
    }

    public void changeStatus(Status newStatus) {
        if (!status.canTransitionTo(newStatus)) {
            throw new IllegalStateException(status + " can't be changed to " + newStatus);
        }
        this.status = newStatus;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getHolderName() {
        return this.holderName;
    }

    public Status getAccountStatus() {
        return status;
    }

    public static void main(String[] args) {
        InterestBearing savingsAccount = new SavingsAccount("1234567890", "John doe", BigDecimal.valueOf(10000), BigDecimal.valueOf(0.1));
        BankAccount currentAccount = new CurrentAccount("1234567890", "John doe", BigDecimal.valueOf(10000), BigDecimal.valueOf(5000));
        System.out.println(currentAccount.getBalance());
        System.out.println(savingsAccount.calculateInterest());

    }

}

