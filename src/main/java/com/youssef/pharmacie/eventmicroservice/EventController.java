package com.youssef.pharmacie.eventmicroservice;

import com.vividsolutions.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

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
}
