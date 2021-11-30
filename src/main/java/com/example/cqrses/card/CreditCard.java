package com.example.cqrses.card;

import com.example.cqrses.card.events.*;
import io.vavr.API;
import io.vavr.Predicates;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static io.vavr.API.$;
import static io.vavr.API.Case;


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
        return io.vavr.collection.List.ofAll(events)
                .foldLeft(new CreditCard(uuid), (creditCard, domainEvent) -> creditCard
                .handleNext(domainEvent));
    }

    private CreditCard handleNext(DomainEvent domainEvent) {
        CreditCard of = API.Match(domainEvent).of(
                Case($(Predicates.instanceOf(LimitAssigned.class)), this::limitAssigned),
                Case($(Predicates.instanceOf(CardWithdrawn.class)), this::cardWithdrawn),
                Case($(Predicates.instanceOf(CardRepaid.class)), this::cardRepaid),
                Case($(Predicates.instanceOf(CycleClosed.class)), this::cycleClosed)
        );
        of.eventsFlushed();
        return of;
    }

    public void assignLimit(BigDecimal amount) {
        if (limitWasAlreadyAssigned()) {
            throw new IllegalStateException();
        }
        limitAssigned(new LimitAssigned(uuid, amount));
    }

    private CreditCard limitAssigned(LimitAssigned event) {
        initialLimit = event.getAmount();
        events.add(event);
        return this;
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
        cardWithdrawn(new CardWithdrawn(uuid, amount));
    }

    private CreditCard cardWithdrawn(CardWithdrawn event) {
        usedLimit = usedLimit.add(event.getAmount());
        withdrawals++;
        events.add(event);
        return this;
    }

    private boolean tooManyWithdrawalsInCycle() {
        return this.withdrawals >= 45;
    }

    private boolean notEnoughMoneyToWithdraw(BigDecimal amount) {
        return availableLimit().compareTo(amount) < 0;
    }

    void repay(BigDecimal amount) {
        cardRepaid(new CardRepaid(uuid, amount));
    }

    private CreditCard cardRepaid(CardRepaid event) {
        usedLimit = usedLimit.subtract(event.getAmount());
        events.add(event);
        return this;
    }

    void billingCycleClosed() {
        //copy pasting somewhere
        cycleClosed(new CycleClosed(uuid));
    }

    private CreditCard cycleClosed(CycleClosed event) {
        withdrawals = 0;
        events.add(event);
        return this;
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
