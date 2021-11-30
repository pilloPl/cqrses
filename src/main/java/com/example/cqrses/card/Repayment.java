package com.example.cqrses.card;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Repayment {

    @Id
    private UUID id;

    private UUID cardId;

    private BigDecimal amount;

    private String terminal;

    private String location;

    private String heavyPdf;

    private Instant date = Instant.now();

    public Repayment(UUID id, UUID cardId, BigDecimal amount, String terminal, String location, String heavyPdf) {
        this.id = id;
        this.amount = amount;
        this.terminal = terminal;
        this.location = location;
        this.heavyPdf = heavyPdf;
        this.cardId = cardId;
    }

    public Repayment() {

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getDate() {
        return date;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getLocation() {
        return location;
    }

    public String getHeavyPdf() {
        return heavyPdf;
    }
}
