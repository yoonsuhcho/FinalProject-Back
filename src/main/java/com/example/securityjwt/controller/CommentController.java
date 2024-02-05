package com.example.securityjwt.controller;

import com.example.securityjwt.controller.response.Response;
import com.example.securityjwt.dto.CommentDTO;
import com.example.securityjwt.model.entity.CommentEntity;
import com.example.securityjwt.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/list/{boardId}")
    @ResponseBody
    public Response<List<CommentDTO>> getAllComment(@PathVariable(value = "boardId") Long boardId) {
//        Assert.notNull(articleId, MessageUtils.getParamNotNull("boardId"));

        List<CommentEntity> commentList = commentService.getAllCommentByBoard(boardId);

        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (CommentEntity commentEntity : commentList) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(commentEntity.getId());
            commentDTO.setWriter(commentEntity.getWriter());
            commentDTO.setBoardId(commentEntity.getBoard().getId());
            commentDTO.setContent(commentEntity.getContent());
            commentDTOList.add(commentDTO);
        }

        return Response.success(commentDTOList);
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public Response<Long> createComment(@RequestBody CommentDTO commentDTO) {
        return Response.success(commentService.createComment(commentDTO).getId());
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public Response<Long>  delete(@PathVariable Long id) {
        return Response.success(commentService.deleteCommentById(id));
    }

}
