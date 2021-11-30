package com.example.cqrses.card;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class CreditCardService {

    private final CreditCardRepository repository;
    private final WithdrawalRepository withdrawalRepository;

    CreditCardService(CreditCardRepository repository, WithdrawalRepository withdrawalRepository) {
        this.repository = repository;
        this.withdrawalRepository = withdrawalRepository;
    }

    @Transactional
    public int withdrawalsCount(UUID cardId) {
        //null checks
        return repository.getById(cardId).getWithdrawals().size();
    }

    @Transactional
    public List<WithdrawalDto> loadWithdrawalsFromCardsWithLimitGreaterThan(BigDecimal limit) {
        //null checks
        return withdrawalRepository
                .findByInitialLimitGreaterThan(limit)
                .stream()
                .map(WithdrawalDto::new)
                .collect(Collectors.toList());
    }

    UUID createNewCard(BigDecimal limit) {
        UUID id = UUID.randomUUID();
        CreditCard card = new CreditCard(id);
        card.assignLimit(limit);
        repository.save(card);
        return id;
    }

    @Transactional
    public void withdraw(UUID cardId, BigDecimal amount) {
        CreditCard card = repository.getById(cardId);
        card.withdraw(amount);
    }

    public DailySummaryDto loadDailySummary() {
        return null;
    }
}
