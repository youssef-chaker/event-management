package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.io.ParseException;

public interface EventRepo {
    void insertEvent(EventDto event) throws ParseException;
}
