package com.project.barter.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.board.dto.BoardPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRepository boardRepository;
    private final ObjectMapper objectMapper;

    @PostMapping
    public void write(@RequestBody BoardPost boardPost, HttpServletResponse response) throws IOException {
        Board requestBoard = objectMapper.convertValue(boardPost, Board.class);
        response.sendRedirect("/board"+requestBoard.getId());
    }

    @GetMapping
    public ResponseEntity findAll(){
        return ResponseEntity.ok().body(boardRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return ResponseEntity.ok().body(boardRepository.findById(id));
    }

}
