package com.example.securityjwt.dto;

import com.example.securityjwt.common.BoardType;
import com.example.securityjwt.model.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;
    private String title;
    private String content;
//    private String season;
    private String writer;
    private BoardType boardType;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long id;
        private String title;
        private String content;
        private String writer;
        private BoardType boardType;
        public BoardEntity toEntity() {
            BoardEntity boardEntity = BoardEntity.builder()
                    .id(id)
                    .title(title)
                    .content(content)
                    .writer(writer)
                    .boardType(boardType)
                    .build();

            return boardEntity;
        }
    }

}
