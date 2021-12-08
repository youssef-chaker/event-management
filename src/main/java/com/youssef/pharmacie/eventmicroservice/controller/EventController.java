package com.youssef.pharmacie.eventmicroservice.controller;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.youssef.pharmacie.eventmicroservice.repo.EventRepo;
import com.youssef.pharmacie.eventmicroservice.proxy.UsersProxy;
import com.youssef.pharmacie.eventmicroservice.dto.EventDto;
import com.youssef.pharmacie.eventmicroservice.entity.Event;
import com.youssef.pharmacie.eventmicroservice.entity.Tag;
import com.youssef.pharmacie.eventmicroservice.entity.User;
import lombok.extern.log4j.Log4j2;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/events")
@Log4j2
public class EventController {

    private final EventRepo eventRepo;
    private final UsersProxy usersProxy;
    private final S3Client s3;
    private final Environment env;

    @Autowired
    public EventController(EventRepo eventRepo, UsersProxy usersProxy, S3Client s3, Environment env) {
        this.eventRepo = eventRepo;
        this.usersProxy = usersProxy;
        this.s3 = s3;
        this.env = env;
    }

    @PostMapping
    public ResponseEntity<?> insertEvent(@RequestBody EventDto eventDto, @AuthenticationPrincipal Jwt jwt){
        try {
            System.out.println(eventDto);
            Geometry geometry = new WKTReader().read(eventDto.getPoint());
            Point point = (Point) geometry;
            Event event = new Event(eventDto.getTitle(),eventDto.getDescription(),point);
            User user = usersProxy.getUserById(
                    jwt.getClaims().get("sub").toString()
            );
//            User user = usersProxy.getUserById("c1e163af-da42-4db5-88f2-c991871d2b88");
            event.setOwner(user);
            event.addTags(
                    Stream.of(eventDto.getTags()).map(Tag::new).collect(Collectors.toList())
//                    eventDto.getTags()
            );
            eventRepo.insertEvent(event);
            return ResponseEntity.ok().build();
        }catch (ParseException e){
            log.error(e);
            return ResponseEntity.badRequest().build();
        }
    }

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

    @PostMapping("/{id}/image")
    public ResponseEntity<?> addImage(@RequestParam("image") MultipartFile file,@PathVariable("id") long id){
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(env.getProperty("s3.bucket"))
                .key(id+".png")
                .build();
        try {
            s3.putObject(objectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable("id") long id){
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(env.getProperty("s3.bucket"))
                .key(id+".png")
                .build();
        var image = s3.getObject(objectRequest, ResponseTransformer.toBytes());
        return ResponseEntity.status(HttpStatus.OK).header("Content-type", MediaType.IMAGE_PNG_VALUE).body(image.asByteArray());
    }
}
