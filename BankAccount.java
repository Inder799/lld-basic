import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private final TransactionRecorder recorder;

    public BankAccount(String accountNumber, String holderName, BigDecimal initialAmount, TransactionRecorder recorder) {
        if (initialAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial Amount can't be negative");
        }
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialAmount;
        this.status = Status.ACTIVE;
        this.recorder = recorder;
    }

    public void deposit(BigDecimal amount) {
        if (!status.canDeposit()) {
            throw new IllegalStateException("Account is not ACTIVE");
        }
        validateAmount(amount);
        balance = balance.add(amount);
        recorder.record(this, amount);
    }

    public final void withdraw(BigDecimal amount) {
        if (!status.canWithdraw()) {
            throw new IllegalStateException("Account is not ACTIVE");
        }
        validateAmount(amount);

        if(!canWithdrawAmount(amount)) {
            throw new IllegalArgumentException("Withdrawal exceeds allowed limit");
        }
        balance = balance.subtract(amount);
        recorder.record(this, amount.negate());
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

}

