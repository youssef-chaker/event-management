package com.youssef.pharmacie.usermicroservice;

import lombok.Data;

@Data
public class Token {
    private String access_token;
    private String refresh_token;
    private String scope;
    private int expires_in;
    private int refresh_expires_in;
}
