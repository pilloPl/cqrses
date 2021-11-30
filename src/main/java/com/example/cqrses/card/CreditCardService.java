package com.example.cqrses.card;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
class CreditCardService {

    private final CreditCardRepository repository;

    CreditCardService(CreditCardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public int withdrawalsCount(UUID cardId) {
        //TODO
        return 0;
    }

    @Transactional
    public List<WithdrawalDto> loadWithdrawalsFromCardsWithLimitGreaterThan(BigDecimal limit) {
        //TODO
        return null;
    }

    UUID createNewCard(BigDecimal limit) {
        //TODO
        return null;
    }

    @Transactional
    public void withdraw(UUID cardId, BigDecimal amount) {
        //TODO
    }
}
