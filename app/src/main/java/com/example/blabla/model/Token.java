package com.example.blabla.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String access_token;
    private String refresh_token;
    private String scope;
    private long expires_in;
    private long refresh_expires_in;

}
