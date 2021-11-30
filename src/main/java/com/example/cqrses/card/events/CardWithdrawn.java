package com.example.cqrses.card.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class CardWithdrawn implements DomainEvent {

    private final UUID cardNo;
    private final BigDecimal amount;

    public CardWithdrawn(UUID cardNo, BigDecimal amount) {
        this.cardNo = cardNo;
        this.amount = amount;
    }

    public UUID getCardNo() {
        return cardNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }



    @Override
    public String getType() {
        return "card-withdrawn";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardWithdrawn that = (CardWithdrawn) o;
        return Objects.equals(cardNo, that.cardNo) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNo, amount);
    }
}
