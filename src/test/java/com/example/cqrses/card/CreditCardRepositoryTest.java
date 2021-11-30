package com.example.cqrses.card;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class CreditCardRepositoryTest {

	@Autowired
	CreditCardRepository creditCardRepository;

	@Test
	@Transactional //just for the sake of the exercise
	void canSaveAndLoad() {
		//given
		UUID uuid = UUID.randomUUID();
		CreditCard card = new CreditCard(uuid);
		card.assignLimit(BigDecimal.TEN);
		card.withdraw(BigDecimal.ONE);
		creditCardRepository.save(card);

		//when
		CreditCard loaded = creditCardRepository.getById(uuid);

		//then
		assertEquals(new BigDecimal(9), loaded.availableLimit());
		assertEquals(1, loaded.getWithdrawals().size());
	}

}
