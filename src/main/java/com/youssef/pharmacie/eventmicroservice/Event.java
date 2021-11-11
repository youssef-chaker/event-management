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

@Entity
@Data
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Point point;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private LongLat longLat;

    public LongLat getLongLat() {
        return new LongLat(String.valueOf(point.getX()),String.valueOf(point.getY()));
    }

    public Event(String title, String description, Point point) {
        this.title = title;
        this.description = description;
        this.point = point;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class LongLat {
    private String longitude;
    private String latitude;
}
