package com.example.securityjwt.repository;

import com.example.securityjwt.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Override
    List<BoardEntity> findAll();

    List<BoardEntity> findAllByWriter(String writer);
}
