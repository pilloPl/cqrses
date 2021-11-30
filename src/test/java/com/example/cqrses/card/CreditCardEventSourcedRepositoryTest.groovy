package com.example.cqrses.card


import spock.lang.Specification

class CreditCardEventSourcedRepositoryTest extends Specification {

    CreditCardEventSourcedRepository creditCardRepository = new CreditCardEventSourcedRepository()

    def 'should save and load card'() {
        given:
            UUID uuid = UUID.randomUUID()
        and:
            CreditCard card = new CreditCard(uuid)
        and:
            card.assignLimit(100)
        and:
            card.withdraw(10)
        when:
            creditCardRepository.save(card)
        and:
            CreditCard loaded = creditCardRepository.load(uuid)
        then:
            loaded.availableLimit() == 90
    }
}
