package com.example.cqrses.card;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class CreditCardServiceIntegrationTest {

	@Autowired
	CreditCardService creditCardService;

	@Test
	void canWithdraw() {
		//given
		UUID newCard = creditCardService.createNewCard(new BigDecimal(100));

		//when
		creditCardService.withdraw(newCard, BigDecimal.TEN);
		creditCardService.withdraw(newCard, BigDecimal.TEN);
		creditCardService.withdraw(newCard, BigDecimal.TEN);

		//then
		assertEquals(2, creditCardService.withdrawalsCount(newCard));
	}

}
