package com.example.securityjwt.dto;

import com.example.securityjwt.model.entity.BoardEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
//@Builder
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    private String writer;

    private String content;

    private Long boardId;

}
