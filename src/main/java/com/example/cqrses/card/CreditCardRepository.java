package com.example.cqrses.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {


    List<CreditCard> findByInitialLimitGreaterThan(BigDecimal limit);



}

interface WithdrawalRepository extends JpaRepository<Withdrawal, UUID> {

    @Query(value = "" +
            "SELECT * FROM WITHDRAWAL w " +
            "JOIN CREDIT_CARD c on w.card_id = c.uuid " +
            "where c.initial_limit > :limit",
            nativeQuery = true)
    List<Withdrawal> findByInitialLimitGreaterThan(BigDecimal limit);

}

