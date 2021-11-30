package com.example.cqrses.card;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class DailySummaryDto {

    Instant date;

    List<WithdrawalDto> withdrawals;

    List<RepaymentDto> repayments;

    List<CreditCardOwnerDto> newCreditCardOwners;

}

class CreditCardOwnerDto {

}

