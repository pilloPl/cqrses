package com.example.cqrses.card;

import org.springframework.stereotype.Service;

@Service
public class DailySummaryCreator {

    DailySummaryDto load() {
        //here goes very heavy sql statement
        //it is frequently invoked
        //and we have no idea how to optimize this JOIN query
        return null;
    }
}
