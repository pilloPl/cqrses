package com.example.cqrses.card;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class CreditCard {

    public CreditCard() {
    }

    @Id
    private UUID uuid = UUID.randomUUID();
    private BigDecimal initialLimit;
    private BigDecimal usedLimit = BigDecimal.ZERO;

    private List<Withdrawal> withdrawals = new ArrayList<>();

    public CreditCard(UUID uuid) {
        this.uuid = uuid;
    }

    public void assignLimit(BigDecimal amount) {
        if (limitWasAlreadyAssigned()) {
            throw new IllegalStateException();
        }
        initialLimit = amount;
    }

    private boolean limitWasAlreadyAssigned() {
        return initialLimit != null;
    }

    public void withdraw(BigDecimal amount) {
        if (notEnoughMoneyToWithdraw(amount)) {
            throw new IllegalStateException();
        }

        if (tooManyWithdrawalsInCycle()) {
            throw new IllegalStateException();
        }
        usedLimit = usedLimit.add(amount);
        withdrawals.add(new Withdrawal(UUID.randomUUID(), this, amount));
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
        return initialLimit.subtract(usedLimit);
    }


    public List<Withdrawal> getWithdrawals() {
        return Collections.unmodifiableList(withdrawals);
    }
}
