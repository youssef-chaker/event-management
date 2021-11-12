package com.youssef.pharmacie.usermicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private UsersRepo userRepo;

    @Autowired
    public UsersController(UsersRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepo.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id){
        return userRepo.getUserById(id);
    }
}
