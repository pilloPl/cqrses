package com.example.cqrses.card.events;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class CycleClosed implements DomainEvent {

    private final UUID cardNo;

    public CycleClosed(UUID cardNo) {
        this.cardNo = cardNo;
    }

    public UUID getCardNo() {
        return cardNo;
    }



    @Override
    public String getType() {
        return "cycle-closed";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CycleClosed that = (CycleClosed) o;
        return Objects.equals(cardNo, that.cardNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNo);
    }
}
