package com.youssef.pharmacie.eventmicroservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tags")
@Data
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    private String value;
    @ManyToOne
    @JoinColumn(name = "event_id",nullable = false)
    @JsonIgnore
    private Event event;

    public Tag(String value) {
        this.value = value;
    }
}
