package com.example.securityjwt.common;

//게시판 종류 enum
public enum BoardType {

    FREE("자유게시판"),
    QNA("질문게시판"),
    NOTICE("공지사항"),
    REVIEW("리뷰게시판");

    private String value;

    BoardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
