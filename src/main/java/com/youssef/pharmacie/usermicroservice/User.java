package com.youssef.pharmacie.usermicroservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vividsolutions.jts.io.WKTReader;
import lombok.Data;
import org.geolatte.geom.Point;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user_entity")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    @NotBlank(message = "username is required")
//    @Size(min = 2,message = "username must be longer than 2 characters")
//    @Column(nullable = false,unique = true)
//    private String username;
//    @NotBlank(message = "email is required")
//    @Email(message = "please provide a valid email address")
//    private String email;
//    @NotBlank(message = "password is required")
//    @Size(min = 6,message = "password must be longer than 6 characters")
//    private String password;
//
//    private Point point;
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "enabled")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled = true;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String password;
    @Transient
    private List<Credentials> credentials;

    public void setPassword(String password) {
        System.out.println("setting credentials");
        this.password = password;
        credentials = new ArrayList<>();
        var cred = new Credentials();
        cred.setValue(password);
        credentials.add(cred);
    }
//    @PrePersist
//    public void encryptPasswordWithBcrypt(){
//        password = new BCryptPasswordEncoder(10).encode(password);
//    }
}
@Data
class Credentials {
    private String type="password";
    private String value ;
    private boolean temporary = false;
}