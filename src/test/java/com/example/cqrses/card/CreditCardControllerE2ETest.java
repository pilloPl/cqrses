package com.example.cqrses.card;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class CreditCardControllerE2ETest {

	@Autowired
	CreditCardController creditCardController;

	@Test
	void canLoadWithdrawalsFromCardWithSpecificLimit() {
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

}
