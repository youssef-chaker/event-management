package com.example.blabla.model;

import lombok.Data;

@Data
public class LoginDto {
    public LoginDto(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
    private String usernameOrEmail;
    private String password;
}
