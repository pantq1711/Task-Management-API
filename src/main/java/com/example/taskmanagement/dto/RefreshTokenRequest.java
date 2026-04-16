package com.example.taskmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshTokenRequest {
    private String token;
}
