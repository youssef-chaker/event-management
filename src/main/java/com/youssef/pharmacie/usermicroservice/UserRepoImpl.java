package com.youssef.pharmacie.usermicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
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

    @Override
    public Token usernameAndPasswordLogin(String username, String password) {
        var map = new HashMap<String,Object>();
        map.put("grant_type","password");
        map.put("client_id","pharmacie-client");
        map.put("client_secret","a520dc74-b8cb-4204-83e1-eaf7b5a5431d");
        map.put("username",username);
        map.put("password",password);
        return keycloackProxy.getUserTokenByUsernameAndPassword(map);
    }
}
