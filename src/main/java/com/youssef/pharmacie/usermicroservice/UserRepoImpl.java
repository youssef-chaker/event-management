package com.youssef.pharmacie.usermicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepoImpl implements UsersRepo{

    private EntityManager entityManager;
    private KeycloackProxy keycloackProxy;

    @Autowired
    public UserRepoImpl(EntityManager entityManager, KeycloackProxy keycloackProxy) {
        this.entityManager = entityManager;
        this.keycloackProxy = keycloackProxy;
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

    @Override
    public void createUser(User user) {
        var token = getAdminToken();
        keycloackProxy.createUser(user,"Bearer "+token.getAccess_token());
    }

    @Override
    public Token getAdminToken() {
        return keycloackProxy.getAdminToken(KeyCloackRequestParams.getParams());
    }
}
