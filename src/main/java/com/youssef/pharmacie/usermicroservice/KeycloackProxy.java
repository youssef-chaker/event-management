package com.youssef.pharmacie.usermicroservice;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "keycloack",url = "http://localhost:8180")
public interface KeycloackProxy {
    @PostMapping(value = "/auth/realms/master/protocol/openid-connect/token",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Token getAdminToken(@RequestBody Map<String,?> keyCloackRequestParams);
    @PostMapping (value = "/auth/admin/realms/pharmacierealm/users")
    void createUser(User user, @RequestHeader("Authorization") String bearerToken);
}
