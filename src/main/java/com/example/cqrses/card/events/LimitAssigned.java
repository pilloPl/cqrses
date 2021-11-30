package com.example.cqrses.card.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class LimitAssigned implements DomainEvent {

    private final UUID cardNo;
    private final BigDecimal amount;

    public LimitAssigned(UUID cardNo, BigDecimal amount) {
        this.cardNo = cardNo;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public UUID getCardNo() {
        return cardNo;
    }

    @Override
    public String getType() {
        return "limit-assigned";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LimitAssigned that = (LimitAssigned) o;
        return Objects.equals(cardNo, that.cardNo) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNo, amount);
    }
}
