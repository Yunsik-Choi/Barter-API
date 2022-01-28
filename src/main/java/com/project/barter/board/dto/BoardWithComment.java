package com.project.barter.board.dto;

import com.project.barter.board.Board;
import com.project.barter.comment.Comment;
import com.project.barter.comment.dto.CommentResponse;
import com.project.barter.user.User;
import com.project.barter.user.dto.UserInfo;
import com.project.barter.user.dto.UserInfoBoard;
import com.project.barter.user.dto.UserSimpleResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class BoardWithComment {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime writeTime;

    private List<CommentResponse> commentList;

    private UserInfoBoard writer;

    public static BoardWithComment byBoard(Board board, List<Comment> commentList){
        User user = board.getUser();
        return BoardWithComment.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writeTime(board.getCreateDate())
                .commentList(CommentResponse.byCommentList(commentList))
                .writer(UserInfoBoard.byUser(user))
                .build();
    }

}
