package com.example.blabla.model;

import lombok.Data;

@Data
public class LocationDto {
    private LocationObj location;

    public LocationDto(String type, double[] coordinates) {
        this.location = new LocationObj(type,coordinates);
    }
}

class LocationObj {
    private String type;
    private double[] coordinates;

    public LocationObj(String type, double[] coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }
}