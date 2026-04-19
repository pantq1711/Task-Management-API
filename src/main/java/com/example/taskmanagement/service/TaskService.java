package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.BoardDTO;
import com.example.taskmanagement.dto.TaskDTO;
import com.example.taskmanagement.entity.Board;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.repository.BoardRepository;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.utils.SecurityUtils;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final SecurityUtils securityUtils;
    private final BoardRepository boardRepository;

    public Page<TaskDTO> getAllTask(Pageable pageable){
        return taskRepository.findAll(pageable).map(this::convertToDTO);
    }

    public Page<TaskDTO> getTasksByUser(Pageable pageable){
        return taskRepository.findAllByUser(securityUtils.getCurrentUser(), pageable)
                .map(this::convertToDTO);
    }

    private Task getTaskByIdAndCheckOwnership(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("task not found with id: " + id));

        boolean isOwner = task.getUser().getId().equals(securityUtils.getCurrentUser().getId());
        boolean isAdmin = securityUtils.getCurrentUser().getRole().equals(User.Role.ADMIN);

        if(!isAdmin && !isOwner){
            throw new AccessDeniedException("You don't have permission to access this task!");
        }
        return task;
    }

    public TaskDTO getTaskById(Long id){
        return convertToDTO(getTaskByIdAndCheckOwnership(id));
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO){
        Task task = getTaskByIdAndCheckOwnership(id);
        task.setName(taskDTO.getName());
        User user = securityUtils.getCurrentUser();
        task.setUser(user);
        task.setStartTime(taskDTO.getStartTime());
        task.setEndTime(taskDTO.getEndTime());
        task.setStatus(taskDTO.getStatus());
        if (taskDTO.getBoardId() != null) {
            Board board = boardRepository.findById(taskDTO.getBoardId())
                    .orElseThrow(() -> new ResourceNotFoundException("Board not found with ID: " + taskDTO.getBoardId()));

            // Phân quyền: Đảm bảo User chỉ được thêm Task vào Board của chính họ
            if (!board.getUser().getId().equals(securityUtils.getCurrentUser().getId())) {
                throw new AccessDeniedException("You don't have permission to access!");
            }
            task.setBoard(board);
        }
        taskRepository.save(task);
        return convertToDTO(task);
    }

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO){
        Task task = new Task();
        task.setName(taskDTO.getName());
        User user = securityUtils.getCurrentUser();
        task.setUser(user);
        task.setStartTime(taskDTO.getStartTime());
        task.setEndTime(taskDTO.getEndTime());
        task.setStatus(taskDTO.getStatus());
        if (taskDTO.getBoardId() != null) {
            Board board = boardRepository.findById(taskDTO.getBoardId())
                    .orElseThrow(() -> new ResourceNotFoundException("Board not found with ID: " + taskDTO.getBoardId()));

            // Phân quyền: Đảm bảo User chỉ được thêm Task vào Board của chính họ
            if (!board.getUser().getId().equals(securityUtils.getCurrentUser().getId())) {
                throw new AccessDeniedException("You don't have permission to access!");
            }
            task.setBoard(board);
        }
        taskRepository.save(task);
        return convertToDTO(task);
    }

    @Transactional
    public void deleteTask(Long id){
        taskRepository.delete(getTaskByIdAndCheckOwnership(id));
    }

    public TaskDTO convertToDTO(Task task){
        return TaskDTO.builder()
                .id(task.getId())
                .userId(task.getUser().getId())
                .name(task.getName())
                .endTime(task.getEndTime())
                .startTime(task.getStartTime())
                .status(task.getStatus())
                .boardId(task.getBoard().getId())
                .build();
    }
}
