package com.example.webapp1a.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.User;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer>{

    Optional<User> findByUsername(String name);
    
}
