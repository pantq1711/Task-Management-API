package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.RefreshToken;
import com.example.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    long countByUser(User user);

    // Lấy token cũ nhất để xóa
    Optional<RefreshToken> findTopByUserOrderByExpiryDateAsc(User user);

    // Cho scheduled cleanup
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.expiryDate < :now OR r.revoked = true")
    void deleteExpiredOrRevoked(@Param("now") Instant now);

    Optional<RefreshToken> findByToken(String token);


    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.user = :user")
    void deleteByUser(User user);
}