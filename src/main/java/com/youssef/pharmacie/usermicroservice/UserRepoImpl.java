package com.youssef.pharmacie.usermicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepoImpl implements UsersRepo{

    private EntityManager entityManager;

    @Autowired
    public UserRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> getAllUsers() {
        var query = entityManager.createQuery("select u from User u");
        return query.getResultList();
    }

    @Override
    public User getUserById(String id) {
        var user = entityManager.find(User.class,id);
        return user;
    }
}
