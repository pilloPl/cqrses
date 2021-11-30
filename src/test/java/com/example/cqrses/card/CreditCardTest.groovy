package com.example.cqrses.card

import com.example.cqrses.card.events.CardWithdrawn
import com.example.cqrses.card.events.LimitAssigned
import spock.lang.Specification

import java.time.Instant


class CreditCardTest extends Specification {

    public static final UUID id = UUID.randomUUID()
    CreditCard card = new CreditCard(id)

    def 'can withdraw'() {
        given:
        CreditCard card = CreditCard.recreateFrom(id,
                [new LimitAssigned(id, 100)])
        when:
        card.withdraw(50)
        then:
        card.getPendingEvents() == [new CardWithdrawn(id, 50)]
    }

    def 'cannot reassign limit'() {
        given:
            card.assignLimit(100)
        when:
            card.assignLimit(200)
        then:
            thrown(IllegalStateException)
    }

    def 'can assign limit'() {
        when:
            card.assignLimit(200)
        then:
            card.availableLimit() == 200
    }

    def 'can repay'() {
        given:
        card.assignLimit(100)
        and:
        card.withdraw(50)
        when:
        card.repay(10)
        then:
        card.availableLimit() == 60
    }


}

