package com.example.cqrses.card;


import java.math.BigDecimal;
import java.time.Instant;

public class RepaymentDto {

    Instant date;

    BigDecimal amount;

    String location;

    String terminal;

    String heavyPdf;

    public RepaymentDto(Repayment repayment) {
        this.date = repayment.getDate();
        this.amount = repayment.getAmount();
        this.location = repayment.getLocation();
        this.terminal = repayment.getTerminal();
        this.heavyPdf = repayment.getHeavyPdf();
    }

}
