package com.example.taskmanagement.service;

import com.example.taskmanagement.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenCleanupService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Scheduled(cron = "0 0 2 * * ?") // Chạy vào lúc 2h sáng mỗi ngày
    public void cleanupTokens() {
        refreshTokenRepository.deleteExpiredOrRevoked(Instant.now());
        System.out.println("Clean refreshToken successfully!");
    }
}