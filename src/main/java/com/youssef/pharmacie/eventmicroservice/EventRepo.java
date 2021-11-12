package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.io.ParseException;

import java.util.List;

public interface EventRepo {
    void insertEvent(Event event) throws ParseException;
    List<Event> getEvents();
}
