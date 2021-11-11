package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class EventRepoImpl implements EventRepo{

    private EntityManager entityManager;

    @Autowired
    public EventRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void insertEvent(EventDto eventDto) throws ParseException {
        Geometry geometry = new WKTReader().read(eventDto.getPoint());
        Point point = (Point) geometry;
        Event event = new Event(eventDto.getTitle(),eventDto.getDescription(),point);
        entityManager.persist(event);
    }
}
