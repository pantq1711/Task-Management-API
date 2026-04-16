package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.AuthResponseDTO;
import com.example.taskmanagement.entity.RefreshToken;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(86400000L * 7)) // Set hạn 7 ngày
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public AuthResponseDTO refreshAccessToken(String requestToken) {

        // 1.Tra cuu Database
        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestToken)
                .orElseThrow(() -> new RuntimeException("Refresh token is not found in database."));

        User user = refreshToken.getUser();

        // 2.Reuse Detection
        if (refreshToken.isRevoked()) {
            refreshTokenRepository.deleteByUser(user);
            throw new RuntimeException("Suspicious activity detected: Refresh token reuse.");
        }

        // 3.check exp
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token was expired. Please make a new sign in request.");
        }

        // 4.token rotation
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        String newAccessToken = jwtService.generateToken(user.getUsername());
        RefreshToken newRefreshToken = createRefreshToken(user);

        // 5.response client
        return AuthResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .username(user.getUsername())
                .build();
    }
}
