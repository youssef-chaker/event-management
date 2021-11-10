package com.example.blabla.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import lombok.Data;

@Data
public class Token {

    private String token = "";
    private String username = "";
    private String email = "";

    public Token() {}

    public Token(String token,String username) {
        this.token = token;
        this.username = username;
    }

    public Token(String token,String username,String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                '}';
    }
}
