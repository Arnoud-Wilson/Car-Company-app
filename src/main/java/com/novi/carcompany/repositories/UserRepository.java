package com.novi.carcompany.repositories;


import com.novi.carcompany.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
