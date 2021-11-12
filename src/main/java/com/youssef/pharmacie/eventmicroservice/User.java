package com.youssef.pharmacie.eventmicroservice;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user_entity")
public class User {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @ManyToMany(mappedBy = "attendees")
    private List<Event> events;
}