package com.example.blabla.room.entity;

import      android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(tableName = "users")
@NoArgsConstructor
public class User {
    @PrimaryKey
    @NonNull
    private String username;
    private String access_token;
    private String refresh_token;
    private String scope;
    private long expires_in;
    private long refresh_expires_in;
//    private Bitmap avatar;


    public User(@NonNull String username, String access_token, String refresh_token, String scope, long expires_in, long refresh_expires_in) {
        this.username = username;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.expires_in = expires_in;
        this.refresh_expires_in = refresh_expires_in;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getRefresh_expires_in() {
        return refresh_expires_in;
    }

    public void setRefresh_expires_in(long refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
    }
}
