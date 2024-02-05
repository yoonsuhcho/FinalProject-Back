package com.example.securityjwt.controller;

import com.example.securityjwt.controller.response.Response;
import com.example.securityjwt.dto.BoardDTO;
import com.example.securityjwt.model.User;
import com.example.securityjwt.model.entity.BoardEntity;
import com.example.securityjwt.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

//    UserDetails userDetails;

    @GetMapping("/edit/{id}")
    @ResponseBody
    public Response<BoardDTO> editBoard(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.getBoardById(id);
        model.addAttribute("board", boardDTO);
        return Response.success(boardDTO);
    }

    @GetMapping("/list")
    @ResponseBody
    public Response<List<BoardDTO>> getBoardList(@AuthenticationPrincipal User userDetails) {
        List<BoardEntity> boardList = boardService.getBoardList(userDetails.getUserid());

        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (BoardEntity boardEntity : boardList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .boardType(boardEntity.getBoardType())
                    .writer(boardEntity.getWriter())
                    .build();
//            BoardDTO boardDTO = BoardDTO.Response(boardEntity);//.toBoardDto();

            boardDTOList.add(boardDTO);
        }

        return Response.success(boardDTOList);
    }


    @PostMapping("/create")
    @ResponseBody
    public Response<Long> create(@AuthenticationPrincipal User userDetails, BoardDTO.Request requestDto) throws Exception {
        requestDto.setWriter(userDetails.getUserid());
        return Response.success(boardService.create(requestDto));
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public Response<Long> update(@PathVariable Long id, BoardDTO boardDTO) throws Exception {

        return Response.success(boardService.update(id, boardDTO));
    }


    @PostMapping("/delete/{id}")
    @ResponseBody
    public Response<Long> delete(@PathVariable Long id, BoardDTO boardDTO) throws Exception {
        boardService.delete(id);
        return Response.success(id);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Response<Long> delete(@PathVariable Long id) {
        boardService.delete(id);
        return Response.success(id);
    }


}
