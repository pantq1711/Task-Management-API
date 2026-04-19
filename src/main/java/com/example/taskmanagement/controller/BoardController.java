package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.BoardDTO;
import com.example.taskmanagement.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Page<BoardDTO>> getAllBoard(Pageable pageable){
        return ResponseEntity.ok(boardService.getAllBoard(pageable));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/my-boards")
    public ResponseEntity<Page<BoardDTO>> getBoardsByUser(Pageable pageable){
        return ResponseEntity.ok(boardService.getBoardsByUser(pageable));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id){
        return ResponseEntity.ok(boardService.getBoardById(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id,@RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.updateBoard(id, boardDTO));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<BoardDTO> createBoard( @RequestBody BoardDTO boardDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(boardDTO));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
