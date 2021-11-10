package com.example.blabla.model;

import java.util.List;

import lombok.Data;

@Data
public class Location {
    private String type;
    //long then lat
    private List<Double> coordinates;

    public Location(String type, List<Double> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }
}
