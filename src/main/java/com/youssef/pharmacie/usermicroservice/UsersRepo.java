package com.youssef.pharmacie.usermicroservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//public interface UsersRepo extends JpaRepository<User,String> {
//}


public interface UsersRepo {
    List<User> getAllUsers();
    User getUserById(String id);
    void createUser(User user);
    Token getAdminToken();
}
