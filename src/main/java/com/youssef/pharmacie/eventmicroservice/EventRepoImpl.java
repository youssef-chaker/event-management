package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EventRepoImpl implements EventRepo{

    private EntityManager entityManager;

    @Autowired
    public EventRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void insertEvent(Event event) throws ParseException {
        entityManager.merge(event);
    }

    @Override
    @Transactional
    public List<Event> getEvents() {
        var query = entityManager.createQuery("select e from Event e ");
        return query.getResultList();
    }

//    @Override
//    public List<Event> getEventsByDistance(int order) {
//      return null;
//    }

    @Override
    @Transactional
    public Event getEvent(long id) {
        return entityManager.find(Event.class,id);
    }

    @Override
    @Transactional
    public void subscribe(User user,long id) {
        var event = entityManager.find(Event.class,id);
        event.addAttendee(user);
    }
}
