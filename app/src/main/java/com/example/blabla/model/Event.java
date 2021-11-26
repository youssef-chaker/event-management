package com.example.blabla.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class Event {
//    @SerializedName("_id")
    private String id ;
    private String title;
    private String description;
    private User owner;
    private String address;
    private Location location;
    private LongLat longLat;
    private double distance;
    private Tag[] tags;
    public Event(String title, String description, String address, LongLat longlat) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.longLat= longlat;
    }
}

