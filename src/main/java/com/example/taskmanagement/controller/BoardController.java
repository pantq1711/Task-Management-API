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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/all")
    public ResponseEntity<Page<BoardDTO>> getAllBoard(Pageable pageable){
        return ResponseEntity.ok(boardService.getAllBoard(pageable));
    }

    @GetMapping("/my-boards")
    public ResponseEntity<Page<BoardDTO>> getBoardsByUser(Pageable pageable){
        return ResponseEntity.ok(boardService.getBoardsByUser(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id){
        return ResponseEntity.ok(boardService.getBoardById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id,@Valid @RequestBody BoardDTO boardDTO){
        return ResponseEntity.ok(boardService.updateBoard(id, boardDTO));
    }

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@Valid @RequestBody BoardDTO boardDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(boardDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
