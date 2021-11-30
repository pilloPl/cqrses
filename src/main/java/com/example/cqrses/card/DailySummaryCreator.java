package com.example.cqrses.card;

public class DailySummaryCreator {

    DailySummaryDto load() {
        //here goes vey heavy sql statement
        //it is frequently invoked
        //and we have no idea how to optimize this JOIN query
        return null;
    }
}
