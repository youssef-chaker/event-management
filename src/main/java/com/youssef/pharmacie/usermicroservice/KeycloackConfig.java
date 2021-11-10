package com.youssef.pharmacie.usermicroservice;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import java.util.ArrayList;

public class KeycloackConfig
{
    static Keycloak keycloak= null;
    final static String serverUrl = "http://localhost:8180/auth";
    final static String realm = "pharmacierealm";
    final static String clientId = "pharmacie-client";
    final static String clientSecret = "2746431f-491c-40f0-b3e2-d1080b6e0d70";
    final static String userName = "youssef";
    final static String password = "youssef";

    public KeycloackConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak!=null)return keycloak;
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .username(userName)
                .password(password)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }

}
