package com.youssef.pharmacie.eventmicroservice;

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

