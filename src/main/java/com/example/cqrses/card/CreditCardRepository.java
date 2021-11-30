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


