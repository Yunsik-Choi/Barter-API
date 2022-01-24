package com.project.barter.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.board.dto.BoardDetail;
import com.project.barter.board.dto.BoardPost;
import com.project.barter.board.dto.BoardPreview;
import com.project.barter.global.GlobalConst;
import com.project.barter.user.User;
import com.project.barter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public void write(@RequestBody BoardPost boardPost, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Board boardRequest = objectMapper.convertValue(boardPost, Board.class);
        User loginUser = userService.findByLoginId(request.getSession(false).getAttribute(GlobalConst.loginSessionAttributeName).toString());
        boardRequest.addUser(loginUser);
        Board writeBoard = boardRepository.save(boardRequest);
        response.sendRedirect("/board/"+writeBoard.getId());
    }

    @GetMapping
    public ResponseEntity findAll(@RequestParam(required = false, name = "preview") boolean preview){
        if(preview==true)
            return ResponseEntity.ok().body(boardRepository.findBoardPreviewAll());
        return ResponseEntity.ok().body(boardRepository.findAll().stream().map(board ->
                BoardDetail.byBoard(board)).toArray());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Optional<Board> findBoardById = boardRepository.findById(id);
        if(findBoardById.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(BoardDetail.byBoard(findBoardById.get()));
    }
}
