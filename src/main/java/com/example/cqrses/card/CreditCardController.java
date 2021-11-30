package com.example.cqrses.card;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
class CreditCardController {

    private final CreditCardService service;

    CreditCardController(CreditCardService service) {
        this.service = service;
    }

    //HTTP Mappings
    public List<WithdrawalDto> loadWithdrawalsFromCardsWithLimitGreaterThan(BigDecimal limit) {
        //null checks
        return service.loadWithdrawalsFromCardsWithLimitGreaterThan(limit);
    }

    //HTTP Mappings
    public UUID createNewCard(BigDecimal limit) {
        return service.createNewCard(limit);
    }

    //HTTP Mappings
    public void withdraw(UUID cardId, BigDecimal amount) {
        service.withdraw(cardId, amount);
    }

    //HTTP Mappings
    public DailySummaryDto loadDailySummary() {
        return service.loadDailySummary();
    }

    //HTTP Mappings
    public void repay(UUID cardId, BigDecimal amount, String location, String terminal, String attachment) {
        service.repay(cardId, amount, location, terminal, attachment);
    }


    public List<RepaymentDto> loadRepayments() {
        return service.loadRepayments();
    }

    public BigDecimal getAvailableLimit(UUID cardId) {
        return service.getAvailableLimit(cardId);
    }

}
