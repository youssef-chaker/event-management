package com.youssef.pharmacie.eventmicroservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.GET;

@FeignClient(name = "user-microservice",url = "http://localhost:8765/api/users")
public interface UsersProxy {
    @GetMapping("/{id}")
    User getUserById(@PathVariable String id);
}
