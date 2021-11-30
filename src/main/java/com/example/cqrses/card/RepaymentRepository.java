package com.example.cqrses.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

interface RepaymentRepository extends JpaRepository<Repayment, UUID> {

}

