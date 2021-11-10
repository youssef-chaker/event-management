package com.youssef.pharmacie.usermicroservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<User,Long> {
}
