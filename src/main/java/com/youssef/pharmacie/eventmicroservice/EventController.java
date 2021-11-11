package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private EventRepo eventRepo;

    @Autowired
    public EventController(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @PostMapping
    public ResponseEntity insertEvent(@RequestBody EventDto eventDto){
        try {
            eventRepo.insertEvent(eventDto);
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
}
