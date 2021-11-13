package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private EventRepo eventRepo;
    private UsersProxy usersProxy;

    @Autowired
    public EventController(EventRepo eventRepo, UsersProxy usersProxy) {
        this.eventRepo = eventRepo;
        this.usersProxy = usersProxy;
    }

    @PostMapping
    public ResponseEntity insertEvent(@RequestBody EventDto eventDto, @AuthenticationPrincipal Jwt jwt){
        try {
            Geometry geometry = new WKTReader().read(eventDto.getPoint());
            Point point = (Point) geometry;
            Event event = new Event(eventDto.getTitle(),eventDto.getDescription(),point);
            User user = usersProxy.getUserById(jwt.getClaims().get("sub").toString());
            event.setOwner(user);
            eventRepo.insertEvent(event);
            return ResponseEntity.ok().build();
        }catch (ParseException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(){
        var events = eventRepo.getEvents();
        return ResponseEntity.ok(events);
    }

    @RequestMapping("/subscribe/{id}")
    public void subscribe(@PathVariable long id,@AuthenticationPrincipal Jwt jwt){
        User user = usersProxy.getUserById(jwt.getClaims().get("sub").toString());
        eventRepo.subscribe(user,id);
    }
}
