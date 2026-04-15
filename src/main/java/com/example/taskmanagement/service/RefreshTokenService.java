package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.RefreshToken;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(86400000L * 7)) // Set hạn 7 ngày
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }
}
