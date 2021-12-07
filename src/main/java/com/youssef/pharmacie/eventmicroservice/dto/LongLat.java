package com.youssef.pharmacie.eventmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LongLat {
    private double longitude;
    private double latitude;
}

