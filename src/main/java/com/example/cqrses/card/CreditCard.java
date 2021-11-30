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


@Entity
public class CreditCard {

    private int withdrawals;

    public CreditCard() {
    }

    @Id
    private UUID uuid = UUID.randomUUID();
    private BigDecimal initialLimit;
    private BigDecimal usedLimit = BigDecimal.ZERO;

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
        withdrawals++;
    }

    private boolean tooManyWithdrawalsInCycle() {
        return this.withdrawals >= 45;
    }

    private boolean notEnoughMoneyToWithdraw(BigDecimal amount) {
        return availableLimit().compareTo(amount) < 0;
    }

    void repay(BigDecimal amount) {
        usedLimit = usedLimit.subtract(amount);
    }

    void billingCycleClosed() {
        //copy pasting somewhere
        withdrawals = 0;
    }

    BigDecimal availableLimit() {
        return initialLimit.subtract(usedLimit);
    }


}
