package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.RefreshToken;
import com.example.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}