package com.example.securityjwt.service;

import com.example.securityjwt.dto.CommentDTO;
import com.example.securityjwt.model.entity.BoardEntity;
import com.example.securityjwt.model.entity.CommentEntity;
import com.example.securityjwt.repository.BoardRepository;
import com.example.securityjwt.repository.CommentRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    public CommentEntity createComment(CommentDTO dto) {

        CommentEntity boardEntity = CommentEntity.builder()
                .writer(dto.getWriter())
                .content(dto.getContent())
                .board(boardRepository.findById(dto.getBoardId()).orElseThrow(
                        () -> new IllegalArgumentException("해당 게시글이 없습니다.")
                ))
                .build();

        return commentRepository.save(boardEntity);
    }

    public List<CommentEntity> getAllCommentByBoard(Long boardId) {

        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        List<CommentEntity> list = commentRepository.findAllByBoard(boardEntity);

        return list;
    }

    public Long deleteCommentById(Long id) {
        commentRepository.deleteById(id);
        return id;
    }

}
