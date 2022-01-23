package com.project.barter.board.dto;

import com.project.barter.board.Board;
import com.project.barter.user.dto.UserInfo;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class BoardDetail {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    private UserInfo user;

    public static BoardDetail byBoard(Board board){
        board.getUser();
        return BoardDetail.builder().id(board.getId()).title(board.getTitle()).content(board.getContent())
                .createDate(board.getCreateDate())
                .modifiedDate(board.getModifiedDate())
                .user(UserInfo.byUser(board.getUser())).build();
    }

}
