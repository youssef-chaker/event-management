package com.youssef.pharmacie.eventmicroservice;

import lombok.Data;

@Data
public class EventDto {
    private String title;
    private String description;
    private LongLat longLat;

    public String getPoint(){
        return "POINT ("+this.longLat.getLongitude()+" "+this.longLat.getLatitude()+")";
    }
}
