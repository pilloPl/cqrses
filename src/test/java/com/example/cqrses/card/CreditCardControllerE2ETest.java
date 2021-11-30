package com.example.cqrses.card;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class CreditCardControllerE2ETest {

	@Autowired
	CreditCardController creditCardController;

	@Test
	void canWithdraw() {
		//given
		UUID cardLowLimit = creditCardController.createNewCard(new BigDecimal(50));
		UUID cardLowLimit2 = creditCardController.createNewCard(new BigDecimal(60));
		UUID cardLowLimit3 = creditCardController.createNewCard(new BigDecimal(70));

		UUID newCard = creditCardController.createNewCard(new BigDecimal(110));
		UUID newCard2 = creditCardController.createNewCard(new BigDecimal(120));
		UUID newCard3 = creditCardController.createNewCard(new BigDecimal(130));

		//when
		creditCardController.withdraw(newCard, BigDecimal.TEN);
		creditCardController.withdraw(newCard, BigDecimal.TEN);
		creditCardController.withdraw(newCard, BigDecimal.TEN);

		creditCardController.withdraw(newCard2, BigDecimal.TEN);
		creditCardController.withdraw(newCard2, BigDecimal.TEN);

		creditCardController.withdraw(newCard3, BigDecimal.TEN);

		//then
		assertEquals(6, creditCardController.
				loadWithdrawalsFromCardsWithLimitGreaterThan(new BigDecimal(100)).size());
	}

	@Test
	void canLoadRepayments() {
		//given
		UUID card = creditCardController.createNewCard(new BigDecimal(50));
		//and
		creditCardController.withdraw(card, BigDecimal.TEN);

		//when
		creditCardController.repay(card, new BigDecimal(5), "location", "terminal", "attachment");

		//then
		assertEquals(1, creditCardController.loadRepayments().size());
		Assertions.assertThat(new BigDecimal(45)).isEqualByComparingTo(creditCardController.getAvailableLimit(card)) ;
		assertEquals("location", creditCardController.loadRepayments().get(0).location);
	}

}
