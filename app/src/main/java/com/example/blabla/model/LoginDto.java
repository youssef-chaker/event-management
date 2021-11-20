package com.example.blabla.model;

import lombok.Data;

@Data
public class LoginDto {
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    private String username;
    private String password;
}
