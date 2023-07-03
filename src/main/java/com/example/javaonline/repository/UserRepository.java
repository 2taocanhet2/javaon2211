package com.example.javaonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.javaonline.entities.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findFirstByEmail(String email);
}
