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
    private final RepaymentRepository repaymentRepository;
    private final DailySummaryCreator dailySummaryCreator;

    CreditCardService(CreditCardRepository repository, WithdrawalRepository withdrawalRepository, RepaymentRepository repaymentRepository, DailySummaryCreator dailySummaryCreator) {
        this.repository = repository;
        this.withdrawalRepository = withdrawalRepository;
        this.repaymentRepository = repaymentRepository;
        this.dailySummaryCreator = dailySummaryCreator;
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
        return dailySummaryCreator.load();
    }

    @Transactional
    public void repay(UUID cardId, BigDecimal amount, String terminal, String location, String attachment) {
        CreditCard card = repository.getById(cardId);
        card.repay(amount);
        repaymentRepository.save(new Repayment(UUID.randomUUID(), cardId, amount, location, terminal, attachment));
    }

    @Transactional
    public List<RepaymentDto> loadRepayments() {
        return repaymentRepository
                .findAll()
                .stream()
                .map(RepaymentDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BigDecimal getAvailableLimit(UUID cardId) {
        return repository.getById(cardId).availableLimit();
    }
}
