package com.youssef.pharmacie.usermicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Token> createUser(@RequestBody User user){
//        return ResponseEntity.ok(user);
        userRepo.createUser(user);
        Token token = userRepo.usernameAndPasswordLogin(user.getUsername(),user.getPassword());
        return ResponseEntity.ok(token);
//        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Token> usernameAndPasswordLogin(@RequestBody LoginDto loginDto){
        var token = userRepo.usernameAndPasswordLogin(loginDto.getUsername(),loginDto.getPassword());
        return ResponseEntity.ok(token);
    }
}
