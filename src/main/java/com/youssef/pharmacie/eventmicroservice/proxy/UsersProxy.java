package com.youssef.pharmacie.eventmicroservice.proxy;


import com.youssef.pharmacie.eventmicroservice.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-microservice",url = "http://localhost:8765/api/users")
public interface UsersProxy {
    @GetMapping("/{id}")
    User getUserById(@PathVariable String id);
}
