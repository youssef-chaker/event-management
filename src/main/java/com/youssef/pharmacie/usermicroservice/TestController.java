package com.youssef.pharmacie.usermicroservice;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/test")
public class TestController {
//    @PostMapping("/createuser")
//    public int createUser(@RequestBody User user){
//        UsersResource usersResource = KeycloackConfig.getInstance().realm(KeycloackConfig.realm).users();
//        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
//        UserRepresentation kcUser = new UserRepresentation();
//        kcUser.setUsername(user.getEmail());
//        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
//        kcUser.setFirstName(user.getUsername());
//        kcUser.setLastName(user.getUsername());
//        kcUser.setEmail(user.getEmail());
//        kcUser.setEnabled(true);
//        kcUser.setEmailVerified(false);
//        var response = usersResource.create(kcUser);
//        return response.getStatus();
//    }
//    private static CredentialRepresentation createPasswordCredentials(String password){
//        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
//        passwordCredentials.setTemporary(false);
//        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
//        passwordCredentials.setValue(password);
//        return passwordCredentials;
//    }
}
