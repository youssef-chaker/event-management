package com.youssef.pharmacie.eventmicroservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.youssef.pharmacie.eventmicroservice.dto.LongLat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "events")
//todo find a way to not specify each field on its own
//todo find a way to make distance field transient and still map it
@SqlResultSetMapping(
        name = "distanceMapping",
        entities =
                @EntityResult(
                        entityClass = Event.class,
                        fields = {
                                @FieldResult(column = "id",name = "id"),
                                @FieldResult(column = "description",name = "description"),
                                @FieldResult(column = "title",name = "title"),
                                @FieldResult(column = "owner_id",name = "owner"),
                                @FieldResult(column = "point",name = "point"),
                                @FieldResult(column = "dist",name = "distance")
                        }
                )
//        columns = @ColumnResult(name = "distance", type = Double.class)
)
@SqlResultSetMapping(
        name = "dist",
        classes = @ConstructorResult(targetClass = Event.class,
        columns = {
                @ColumnResult(name = "id",type = long.class),
                @ColumnResult(name = "title",type = String.class),
                @ColumnResult(name = "description",type = String.class),
                @ColumnResult(name = "point",type = String.class),
                @ColumnResult(name = "distance",type = double.class)
        })
)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "event")
    private List<Tag> tags;
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
    @Transient
    private double distance;
    public LongLat getLongLat() {
        if(point==null)return null;
        return new LongLat(point.getX(),point.getY());
    }

    public void addAttendee(User user){
        if(attendees==null)attendees = new ArrayList<>();
        attendees.add(user);
    }

    public void addTags(List<Tag> tagList){
        if(this.tags == null) this.tags = new ArrayList<>();
//        var tagList =List.of(tags);
        tagList.forEach(tag -> tag.setEvent(this));
        this.tags.addAll(tagList);
    }

    public Event(String title, String description, Point point) {
        this.title = title;
        this.description = description;
        this.point = point;
    }

    public Event(long id,String title, String description, double distance) {
        this.title = title;
        this.description = description;
        this.distance = distance;
        this.id = id;
    }

    public Event(long id,String title, String description, String point,double distance) throws ParseException {
        this.id = id;
        this.title = title;
        this.description = description;
        this.point = (Point) new WKTReader().read(point);
        this.distance=distance;
    }

}
