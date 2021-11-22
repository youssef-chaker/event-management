package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.persistence.OrderBy;
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
            User user = usersProxy.getUserById(
                    jwt.getClaims().get("sub").toString()
            );
//            User user = usersProxy.getUserById("c1e163af-da42-4db5-88f2-c991871d2b88");
            event.setOwner(user);
            eventRepo.insertEvent(event);
            return ResponseEntity.ok().build();
        }catch (ParseException e){
            return ResponseEntity.badRequest().build();
        }
    }


//    @GetMapping
//    public ResponseEntity<List<Event>> getAllEvents(@RequestBody LongLat longLat, @RequestParam String orderBy,@RequestParam int order,@RequestParam int limit,@RequestParam int offset){
//        List<Event> events ;
//        if(orderBy != null){
//            events = eventRepo.getEvents();
//        }else{
//            events = eventRepo.getEvents();
//        }
//        return ResponseEntity.ok(events);
//    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(@RequestParam(required = false) Double longitude,@RequestParam(required = false) Double latitude){
        System.out.println(longitude+ " "+latitude);
        List<Event> events;
        if(longitude!=null && latitude!=null){
            System.out.println("inside if ");
            events = eventRepo.getEventsByDistance(longitude,latitude,1);
        } else {
            events =eventRepo.getEvents();
        }
        return ResponseEntity.ok(events);
    }


    @GetMapping("{id}")
    public ResponseEntity<Event> getEventById(@PathVariable long id){
        var event = eventRepo.getEvent(id);
        return ResponseEntity.ok(event);
    }

    @RequestMapping("/subscribe/{id}")
    @PreAuthorize("isAuthenticated()")
    public void subscribe(@PathVariable long id,@AuthenticationPrincipal Jwt jwt){
        User user = usersProxy.getUserById(jwt.getClaims().get("sub").toString());
        eventRepo.subscribe(user,id);
    }
}
