package com.youssef.pharmacie.eventmicroservice.repo;

import com.vividsolutions.jts.io.ParseException;
import com.youssef.pharmacie.eventmicroservice.entity.Event;
import com.youssef.pharmacie.eventmicroservice.entity.User;

import java.util.List;

public interface EventRepo {
    void insertEvent(Event event) throws ParseException;
    List<Event> getEvents();
    List<Event> getEventsByDistance(double longitude,double latitude,int order);
    Event getEvent(long id);
    void subscribe(User user , long id);

}
