package com.project.barter.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.board.dto.BoardPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRepository boardRepository;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity write(@RequestBody BoardPost boardPost){
        Board requestBoard = objectMapper.convertValue(boardPost, Board.class);
        return ResponseEntity.ok().body(boardRepository.save(requestBoard));
    }

}
