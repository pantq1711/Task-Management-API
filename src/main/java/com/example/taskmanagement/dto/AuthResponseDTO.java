package com.example.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String refreshToken;

    private String accessToken;

    private String username;

    private Long userId;        // Trả về cho Frontend dùng

    private LocalDateTime refreshTokenExpiry; // Trả về để Frontend biết khi nào cần refresh
}
