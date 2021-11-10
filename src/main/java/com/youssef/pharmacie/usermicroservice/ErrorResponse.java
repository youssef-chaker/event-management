package com.youssef.pharmacie.usermicroservice;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class ErrorResponse {
    private String title;
    private String detail;
    private HashMap<Object,Object> errors = new HashMap<>();
    public ErrorResponse() {
    }

    public ErrorResponse(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }
}
