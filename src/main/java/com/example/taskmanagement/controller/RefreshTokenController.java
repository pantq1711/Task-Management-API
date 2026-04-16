package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.AuthResponseDTO;
import com.example.taskmanagement.dto.RefreshTokenRequest;
import com.example.taskmanagement.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequest request) {
        AuthResponseDTO response = refreshTokenService.refreshAccessToken(request.getToken());
        return ResponseEntity.ok(response);
    }
}
