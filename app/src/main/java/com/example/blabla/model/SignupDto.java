package com.example.blabla.model;

import lombok.Data;

@Data
public class SignupDto {

    public SignupDto(String username,String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    private String username;
    private String email;
    private String password;
}
