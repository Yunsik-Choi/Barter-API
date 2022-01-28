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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public void write(@RequestBody BoardPost boardPost,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionLoginIdAttribute = getSessionLoginIdAttribute(request);
        Board board = boardService.save(boardPost, sessionLoginIdAttribute);
        response.sendRedirect("/board/"+board.getId());
    }

    @GetMapping("/preview")
    public ResponseEntity<EntityBody<List<BoardPreview>>> findPreview(){
        List<BoardPreview> boardPreviewAll = boardService.findBoardPreviewAll();
        return ResponseEntity.ok().body(new EntityBody<>(boardPreviewAll));
    }

    @GetMapping
    public ResponseEntity<EntityBody<List<BoardResponse>>> findAll(){
        List<BoardResponse> boardResponseList = boardService.findAll();
        return ResponseEntity.ok().body(new EntityBody<>(boardResponseList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityBody<BoardWithComment>> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(new EntityBody<>(boardService.findBoardWithCommentById(id)));
    }

    @PostMapping("/{id}/comment")
    public void comment(@PathVariable Long id, @RequestBody CommentPost commentPost,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        Board board = boardService.addComment(id, commentPost, getSessionLoginIdAttribute(request));
        response.sendRedirect("/board/"+board.getId());
    }

    @PostMapping("/{id}/comment/{commentId}/subcomment")
    public void subComment(@PathVariable(name = "id") Long boardId, @PathVariable(name = "commentId") Long commentId,
                           @RequestBody CommentPost commentPost,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
        boardService.addSubComment(boardId, commentId, commentPost, getSessionLoginIdAttribute(request));
        response.sendRedirect("/board/"+boardId);
    }

    private String getSessionLoginIdAttribute(HttpServletRequest request) {
        return request.getSession(false).getAttribute(GlobalConst.loginSessionAttributeName).toString();
    }

}
