package com.example.taskmanagement.dto;

import com.example.taskmanagement.entity.Board;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDTO {

    private Long id;

    private Task.Status status;

    private String name;

    private Long boardId;

    private Long userId;

    private LocalDateTime startTime, endTime;
}
