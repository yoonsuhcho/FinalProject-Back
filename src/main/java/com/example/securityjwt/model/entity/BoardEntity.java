package com.example.securityjwt.model.entity;

import com.example.securityjwt.common.BoardType;
import com.example.securityjwt.dto.BoardDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//사용자게시글  DTO엔 String 타입의 createdAt(작성일) 변수 있어야함, 다중파일 받는것도!!
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Builder
@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String season;
    @Column(length = 50)
    private String writer;
    @Column(length = 50)
    private String title;
    @Column(length = 500)
    private String content;

    //게시판 종류
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private BoardType boardType;


    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private final List<CommentEntity> comment = new ArrayList<>();

    public void addComment(CommentEntity comment) {
        this.comment.add(comment);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(comment.getBoard() != this)
            // 파일 저장
            comment.setBoard(this);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
