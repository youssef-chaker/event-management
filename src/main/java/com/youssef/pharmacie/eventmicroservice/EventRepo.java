package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.io.ParseException;

import java.util.List;

public interface EventRepo {
    void insertEvent(EventDto event) throws ParseException;
    List<Event> getEvents();
}
