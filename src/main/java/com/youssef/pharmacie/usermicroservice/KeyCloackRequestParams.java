package com.youssef.pharmacie.usermicroservice;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public final class KeyCloackRequestParams {
    private final static String username = "youssef";
    private final static String password = "youssef";
    private final static String grant_type = "password";
    private final static String client_id = "admin-cli";
    public static Map<String,?> getParams(){
        var map = new HashMap<String,Object>();
        map.put("username",username);
        map.put("password",password);
        map.put("grant_type",grant_type);
        map.put("client_id",client_id);
        return map;
    }
}
