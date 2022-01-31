package com.project.barter.comment.dto;

import com.project.barter.board.Board;
import com.project.barter.comment.Comment;
import com.project.barter.comment.SubComment.SubComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CommentPost {

    private String content;

    public Comment toComment(String loginId, Board board){
        return Comment.builder()
                .content(content)
                .writerLoginId(loginId)
                .board(board)
                .build();
    }

    public SubComment toSubComment(String loginId, Comment comment){
        SubComment subComment = SubComment.builder()
                .content(content)
                .writerLoginId(loginId)
                .build();
        comment.addSubComment(subComment);
        return subComment;
    }
}
