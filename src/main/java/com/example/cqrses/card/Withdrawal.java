package com.example.cqrses.card;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Withdrawal {

    @Id
    private UUID id;

    private CreditCard card;

    private BigDecimal amount;

    private Instant date = Instant.now();

    public Withdrawal(UUID id, CreditCard card, BigDecimal amount) {
        this.id = id;
        this.card = card;
        this.amount = amount;
    }

    public Withdrawal() {

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getDate() {
        return date;
    }

}
