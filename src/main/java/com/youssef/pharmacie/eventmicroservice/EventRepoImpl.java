package com.youssef.pharmacie.eventmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EventRepoImpl implements EventRepo{

    private final EntityManager entityManager;

    @Autowired
    public EventRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void insertEvent(Event event) {
        entityManager.merge(event);
    }

    @Override
    @Transactional
    public List<Event> getEvents() {
        var query = entityManager.createQuery("select e from Event e ",Event.class);
        return query.getResultList();
    }

    @Override
    public List<Event> getEventsByDistance (double longitude,double latitude,int order) {
        String ascORdesc = order==-1 ? "desc": "asc";
        var query = entityManager.
                createQuery("select e from Event e order by ST_Distance(ST_MakePoint("+longitude+","+latitude+"), point) "+ascORdesc,Event.class);
        return query.getResultList();
    }

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
