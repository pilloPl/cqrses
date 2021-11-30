package com.example.cqrses.card;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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

}
