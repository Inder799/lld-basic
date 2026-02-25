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

class BankAccount {
    private final String accountNumber;
    private final String holderName;
    private BigDecimal balance;
    private Status accountStatus;

    public BankAccount(String accountNumber, String holderName, BigDecimal initialAmount) {
        if (initialAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial Amount can't be negative");
        }
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialAmount;
        this.accountStatus = Status.ACTIVE;
    }

    public void deposit(BigDecimal amount) {
        if (!accountStatus.canDeposit()) {
            throw new IllegalStateException("Account is not ACTIVE");
        }
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (!accountStatus.canWithdraw()) {
            throw new IllegalStateException("Account is not ACTIVE");
        }
        validateAmount(amount);

        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Amount entered is greater than available balance. Please add less amount");
        }

        this.balance = this.balance.subtract(amount);
    }

    // private void validateActive() {
    // if (accountStatus != Status.ACTIVE) {
    // throw new IllegalStateException("Account is not ACTIVE");
    // }
    // }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount can't be negative");
        }
    }

    public void changeStatus(Status newStatus) {
        if (!accountStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException(accountStatus + " can't be changed to " + newStatus);
        }
        this.accountStatus = newStatus;
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
        return accountStatus;
    }

}

public static void main(String[] args) {
    BankAccount bankAccount = new BankAccount();
    bankAccount.accountNumber = "1234567890";
    bankAccount.holderName = "John doe";
    bankAccount.balance = 23523;

    bankAccount.deposit(2000);
    bankAccount.withdraw(5000);
    System.out.println(bankAccount.getBalance());
}