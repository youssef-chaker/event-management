package com.youssef.pharmacie.eventmicroservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vividsolutions.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Point point;
    @Transient
    private LongLat longLat;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToMany
    @JoinTable(name = "user_event",joinColumns = @JoinColumn(name = "event_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> attendees;
    public LongLat getLongLat() {
        return new LongLat(String.valueOf(point.getX()),String.valueOf(point.getY()));
    }

    public void addAttendee(User user){
        if(attendees==null)attendees = new ArrayList<>();
        attendees.add(user);
    }

    public Event(String title, String description, Point point) {
        this.title = title;
        this.description = description;
        this.point = point;
    }
}
