package com.example.taskmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {

    private String refreshToken;

    private String accessToken;

    private String username;
}
