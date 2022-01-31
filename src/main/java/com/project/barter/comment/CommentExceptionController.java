package com.project.barter.comment;

import com.project.barter.board.exception.BoardNotExistsException;
import com.project.barter.comment.exception.CommentNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CommentExceptionController {

    @ExceptionHandler(CommentNotExistsException.class)
    public ResponseEntity commentNotExists(CommentNotExistsException exception){
        log.error("Comment Not Exists");
        return ResponseEntity.notFound().build();
    }

}
