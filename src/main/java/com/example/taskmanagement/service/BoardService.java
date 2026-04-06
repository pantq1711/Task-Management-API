package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.BoardDTO;
import com.example.taskmanagement.entity.Board;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.repository.BoardRepository;
import com.example.taskmanagement.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    private final SecurityUtils securityUtils;

    public Page<BoardDTO> getAllBoard(Pageable pageable){
        return boardRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Page<BoardDTO> getBoardsByUser(Pageable pageable){
        return boardRepository.findAllByUser(securityUtils.getCurrentUser(), pageable)
                .map(this :: convertToDTO);
    }

    private Board getBoardByIdAndCheckOwnership(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Board not found with id: " + id));
        if(!board.getUser().getId().equals(securityUtils.getCurrentUser().getId())){
            throw new AccessDeniedException("You don't have permission to access this board!");
        }
        return board;
    }

    public BoardDTO getBoardById(Long id){
        return convertToDTO(getBoardByIdAndCheckOwnership(id));
    }

    public BoardDTO updateBoard(Long id, BoardDTO boardDTO){
        Board board = getBoardByIdAndCheckOwnership(id);
        board.setName(boardDTO.getName());
        boardRepository.save(board);
        return convertToDTO(board);
    }

    public BoardDTO createBoard(BoardDTO boardDTO){
        Board board = new Board();
        board.setName(boardDTO.getName());
        User user = securityUtils.getCurrentUser();
        board.setUser(user);
        boardRepository.save(board);
        return convertToDTO(board);

    }

    public void deleteBoard(Long id){
        boardRepository.delete(getBoardByIdAndCheckOwnership(id));
    }

    public BoardDTO convertToDTO(Board board){
        return BoardDTO.builder()
                .id(board.getId())
                .userId(board.getUser().getId())
                .name(board.getName())
                .build();
    }

}
