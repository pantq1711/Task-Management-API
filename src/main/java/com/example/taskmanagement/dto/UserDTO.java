package com.example.taskmanagement.dto;

import com.example.taskmanagement.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String name;

    private User.Role role;
}
