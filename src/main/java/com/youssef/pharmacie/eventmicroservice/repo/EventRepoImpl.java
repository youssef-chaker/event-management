package com.youssef.pharmacie.eventmicroservice.repo;

import com.youssef.pharmacie.eventmicroservice.entity.Event;
import com.youssef.pharmacie.eventmicroservice.entity.Tag;
import com.youssef.pharmacie.eventmicroservice.entity.User;
import com.youssef.pharmacie.eventmicroservice.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EventRepoImpl implements EventRepo {

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
    @Transactional
    public List<Event> getEventsByDistance (double longitude,double latitude,int order) {
        String ascORdesc = order==-1 ? "desc": "asc";
//        var query = entityManager.
//                createQuery("select e.id,e.title,e.description from Event e order by ST_Distance(ST_MakePoint(7,35), point) "+ascORdesc);
//        var query = entityManager.createNativeQuery("select id,title,description,owner_id,ST_Distance(ST_MakePoint("+longitude+","+latitude+"), point) as distance from events order by distance","distanceMapping");
        var query = entityManager.createNativeQuery("select id,title,description,st_astext(point) as point,ST_Distance( cast(ST_MakePoint("+longitude+","+latitude+") as geography) , cast(point as geography) ) as distance from events order by distance","dist");
        var resultset = (List<Event>)query.getResultList();
        resultset.forEach(e -> {
            e.setTags(entityManager.createQuery("select t from tags t where t.event.id="+e.getId(), Tag.class).getResultList());
        });
        return resultset;
    }

    @Override
    @Transactional
    public Event getEvent(long id) {
        return entityManager.find(Event.class,id);
    }

    @Override
    @Transactional
    public void subscribe(User user, long id) {
        var event = entityManager.find(Event.class,id);
        event.addAttendee(user);
    }
}
