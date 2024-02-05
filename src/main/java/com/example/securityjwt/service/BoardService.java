package com.example.securityjwt.service;

import com.example.securityjwt.dto.BoardDTO;
import com.example.securityjwt.model.entity.BoardEntity;
import com.example.securityjwt.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardEntity> getBoardList(String userid) {
        return boardRepository.findAll();
    }

    public List<BoardEntity> getMyBoardList(String userid) {

        /*내 작성글*/
        List<BoardEntity> list1 = boardRepository.findAllByWriter(userid);

        return list1;
    }

    @Transactional
    public Long create(
            BoardDTO.Request boardDTO
    ) throws Exception {
        // 파일 처리를 위한 Board 객체 생성
        BoardEntity boardEntity = boardDTO.toEntity();

        return boardRepository.save(boardEntity).getId();
    }

    public BoardDTO getBoardById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        BoardDTO boardDTO = BoardDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .boardType(boardEntity.getBoardType())
                .build();

        return boardDTO;
    }

    public Long update(Long id, BoardDTO boardDTO
                       ) throws Exception {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        boardEntity.update(boardDTO.getTitle(), boardDTO.getContent());

        return boardRepository.save(boardEntity).getId();
    }

    public void delete(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        boardRepository.delete(boardEntity);
    }
}
