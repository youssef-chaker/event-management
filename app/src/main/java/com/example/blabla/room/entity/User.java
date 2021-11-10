package com.example.blabla.room.entity;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import lombok.Data;

@Data
@Entity(tableName = "users")
public class User {
    private String token;
    private String username;
    @PrimaryKey
    @NonNull
    private String email;
    private Bitmap avatar;

    public User(String token, String username, @NotNull String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
