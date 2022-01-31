package com.project.barter.board;

import com.project.barter.board.dto.BoardPost;
import com.project.barter.board.dto.BoardPreview;
import com.project.barter.board.dto.BoardResponse;
import com.project.barter.board.dto.BoardWithComment;
import com.project.barter.board.service.BoardService;
import com.project.barter.comment.dto.CommentPost;
import com.project.barter.global.GlobalConst;
import com.project.barter.global.dto.EntityBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public void saveBoard(@RequestBody BoardPost boardPost,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start Board Write");
        String sessionLoginIdAttribute = getSessionLoginIdAttribute(request);
        Board board = boardService.saveBoard(boardPost, sessionLoginIdAttribute);
        response.sendRedirect("/board/"+board.getId());
    }

    @GetMapping("/preview")
    public ResponseEntity<EntityBody<List<BoardPreview>>> findPreview(){
        log.info("Start Find All Boards Preview");
        List<BoardPreview> boardPreviewAll = boardService.findBoardPreviewAll();
        return ResponseEntity.ok().body(new EntityBody<>(boardPreviewAll));
    }

    @GetMapping
    public ResponseEntity<EntityBody<List<BoardResponse>>> findAll(){
        log.info("Start Find All Boards");
        List<BoardResponse> boardResponseList = boardService.findAllBoards();
        return ResponseEntity.ok().body(new EntityBody<>(boardResponseList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityBody<BoardWithComment>> findById(@PathVariable Long id){
        log.info("Start Find Board {}",id);
        return ResponseEntity.ok().body(new EntityBody<>(boardService.findBoardWithCommentById(id)));
    }

    @PostMapping("/{boardId}/comment")
    public void comment(@PathVariable Long boardId, @RequestBody CommentPost commentPost,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start Board {} Add Comment",boardId);
        Board board = boardService.addComment(boardId, commentPost, getSessionLoginIdAttribute(request));
        response.sendRedirect("/board/"+board.getId());
    }

    @PostMapping("/{boardId}/comment/{commentId}/subcomment")
    public void subComment(@PathVariable(name = "boardId") Long boardId, @PathVariable(name = "commentId") Long commentId,
                           @RequestBody CommentPost commentPost,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start Board {} Comment {} Add Sub Comment",boardId,commentId);
        boardService.addSubComment(boardId, commentId, commentPost, getSessionLoginIdAttribute(request));
        response.sendRedirect("/board/"+boardId);
    }

    private String getSessionLoginIdAttribute(HttpServletRequest request) {
        String loginId = request.getSession(false).getAttribute(GlobalConst.loginSessionAttributeName).toString();
        log.info("User Login ID : {}",loginId);
        return loginId;
    }

}
