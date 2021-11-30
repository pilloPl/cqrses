package com.example.cqrses.card;

import com.example.cqrses.card.events.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;


public class CreditCard {

    private int withdrawals;

    public CreditCard() {
    }

    private UUID uuid = UUID.randomUUID();
    private BigDecimal initialLimit;
    private BigDecimal usedLimit = BigDecimal.ZERO;
    private List<DomainEvent> events = new ArrayList<>();

    public CreditCard(UUID uuid) {
        this.uuid = uuid;
    }

    public static CreditCard recreateFrom(UUID uuid, List<DomainEvent> events) {
        return null;
    }

    public void assignLimit(BigDecimal amount) {
        if (limitWasAlreadyAssigned()) {
            throw new IllegalStateException();
        }
        limitAssigned(new LimitAssigned(uuid, amount, Instant.now()));
    }

    private void limitAssigned(LimitAssigned event) {
        initialLimit = event.getAmount();
        events.add(event);
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
        cardWithdrawn(new CardWithdrawn(uuid, amount, Instant.now()));
    }

    private void cardWithdrawn(CardWithdrawn event) {
        usedLimit = usedLimit.add(event.getAmount());
        withdrawals++;
        events.add(event);
    }

    private boolean tooManyWithdrawalsInCycle() {
        return this.withdrawals >= 45;
    }

    private boolean notEnoughMoneyToWithdraw(BigDecimal amount) {
        return availableLimit().compareTo(amount) < 0;
    }

    void repay(BigDecimal amount) {
        cardRepaid(new CardRepaid(uuid, amount, Instant.now()));
    }

    private void cardRepaid(CardRepaid event) {
        usedLimit = usedLimit.subtract(event.getAmount());
        events.add(event);
    }

    void billingCycleClosed() {
        //copy pasting somewhere
        cycleClosed(new CycleClosed(uuid, Instant.now()));
    }

    private void cycleClosed(CycleClosed event) {
        withdrawals = 0;
        events.add(event);
    }

    BigDecimal availableLimit() {
        return initialLimit.subtract(usedLimit);
    }


    public UUID getUuid() {
        return uuid;
    }

    public List<DomainEvent> getPendingEvents() {
        return Collections.unmodifiableList(events);
    }

    public void eventsFlushed() {
        events.clear();
    }
}
