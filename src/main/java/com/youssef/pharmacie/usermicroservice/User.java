package com.youssef.pharmacie.usermicroservice;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "username is required")
    @Size(min = 2,message = "username must be longer than 2 characters")
    @Column(nullable = false,unique = true)
    private String username;
    @NotBlank(message = "email is required")
    @Email(message = "please provide a valid email address")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 6,message = "password must be longer than 6 characters")
    private String password;
//    @PrePersist
//    public void encryptPasswordWithBcrypt(){
//        password = new BCryptPasswordEncoder(10).encode(password);
//    }
}
