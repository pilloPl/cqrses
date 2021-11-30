package com.example.cqrses.card;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CreditCard {

    private final UUID uuid;
    private BigDecimal limit;
    private BigDecimal usedLimit = BigDecimal.ZERO;
    private List<Withdrawal> withdrawals = new ArrayList<>();

    public CreditCard(UUID uuid) {
        this.uuid = uuid;
    }

    public void assignLimit(BigDecimal amount) {
        if (limitWasAlreadyAssigned()) {
            throw new IllegalStateException();
        }
        limit = amount;
    }

    private boolean limitWasAlreadyAssigned() {
        return limit != null;
    }

    public void withdraw(BigDecimal amount) {
        if (notEnoughMoneyToWithdraw(amount)) {
            throw new IllegalStateException();
        }

        if (tooManyWithdrawalsInCycle()) {
            throw new IllegalStateException();
        }
        usedLimit = usedLimit.add(amount);
        withdrawals.add(new Withdrawal());
    }

    private boolean tooManyWithdrawalsInCycle() {
        return this.withdrawals.size() >= 45;
    }

    private boolean notEnoughMoneyToWithdraw(BigDecimal amount) {
        return availableLimit().compareTo(amount) < 0;
    }

    void repay(BigDecimal amount) {
        usedLimit = usedLimit.subtract(amount);
    }

    void billingCycleClosed() {
        //copy pasting somewhere
        withdrawals.clear();
    }

    BigDecimal availableLimit() {
        return limit.subtract(usedLimit);
    }


}
