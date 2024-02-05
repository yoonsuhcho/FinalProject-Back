package com.example.securityjwt.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(length = 50)
    private String writer;

    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    //constructor
    @Builder
    public CommentEntity(Long id, String writer, String content, BoardEntity board) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.board = board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }
}
