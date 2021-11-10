package com.example.blabla.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class Event {
    @SerializedName("_id")
    private String id ;
    private String title;
    private String description;
    private User owner;
    private String address;
    private Location location;

    public Event(String title, String description, String address, Location location) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.location = location;
    }
}

