package com.youssef.pharmacie.eventmicroservice;

import lombok.Data;

@Data
public class EventDto {
    private String title;
    private String description;
    private double longitude;
    private double latitude;

    public String getPoint(){
        return "POINT ("+this.longitude+" "+this.latitude+")";
    }
}
