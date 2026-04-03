package com.example.taskmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshTokenDTO {

    private Long id;

    private String token;

    private Long userId;

    private LocalDateTime expiredAt;
}
