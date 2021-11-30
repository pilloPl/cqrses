package com.example.cqrses.card;

import com.example.cqrses.card.events.DomainEvent;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CreditCardEventSourcedRepository {

    private final Map<UUID, List<DomainEvent>> eventStreams = new HashMap<>();


    public void save(CreditCard creditCard) {
        List<DomainEvent> currentStream
                = eventStreams.getOrDefault(creditCard.getUuid(), new ArrayList<>());
        currentStream.addAll(creditCard.getPendingEvents());
        creditCard.getPendingEvents().forEach(event ->
        eventStreams.put(creditCard.getUuid(), currentStream));
        creditCard.eventsFlushed();
    }

    public CreditCard load(UUID uuid) {
        return CreditCard.recreateFrom(uuid, eventStreams.getOrDefault(uuid, new ArrayList<>()));
    }
}
