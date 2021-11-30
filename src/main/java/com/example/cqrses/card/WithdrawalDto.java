package com.example.cqrses.card;


import java.math.BigDecimal;
import java.time.Instant;

public class WithdrawalDto {

    Instant date;

    BigDecimal amount;

    public WithdrawalDto(Withdrawal withdrawal) {
        this.date = withdrawal.getDate();
        this.amount = withdrawal.getAmount();
    }

}
