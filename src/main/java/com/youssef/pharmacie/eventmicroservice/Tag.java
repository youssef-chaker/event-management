package com.youssef.pharmacie.eventmicroservice;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "tag")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String value;
    @ManyToOne
    @JoinColumn(name = "tag_id",nullable = false)
    private Tag tag;
}
