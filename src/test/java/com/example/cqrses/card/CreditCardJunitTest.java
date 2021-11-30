package com.example.cqrses.card;

import com.example.cqrses.card.events.CardWithdrawn;
import com.example.cqrses.card.events.LimitAssigned;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardJunitTest {

    UUID id = UUID.randomUUID();

    @Test
    void canWithdraw() {
        //given
        CreditCard card = CreditCard
                .recreateFrom(id, List.of(new LimitAssigned(id, new BigDecimal(100))));

        //when
        card.withdraw(new BigDecimal(10));

        //then
        assertThat(card.getPendingEvents()).containsExactly(new CardWithdrawn(id, new BigDecimal(10)));
    }

    @Test
    void cannotReassignLimit() {
        //when
        CreditCard card = new CreditCard();
        card.assignLimit(new BigDecimal(100));

        //when
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> card.assignLimit(BigDecimal.TEN));

    }

    @Test
    void canAssignLimit() {
        //when
        CreditCard card = new CreditCard();
        card.assignLimit(new BigDecimal(100));

        //then
        assertThat(card.availableLimit()).isEqualByComparingTo(new BigDecimal(100));
    }

    @Test
    void canRepayLimit() {
        //given
        CreditCard card = new CreditCard();
        card.assignLimit(new BigDecimal(100));

        //and
        card.withdraw(BigDecimal.TEN);

        //when
        card.repay(BigDecimal.TEN);

        //then
        assertThat(card.availableLimit()).isEqualByComparingTo(new BigDecimal(100));
    }

}