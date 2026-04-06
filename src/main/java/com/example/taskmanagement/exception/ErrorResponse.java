package com.example.taskmanagement.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private LocalDateTime time;

    private String message;

    private String detail;

    public ErrorResponse(String message, String detail){
        this.message = message;
        this.detail = detail;
        this.time = LocalDateTime.now();
    }
}
