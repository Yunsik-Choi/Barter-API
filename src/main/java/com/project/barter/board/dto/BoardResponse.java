package com.project.barter.board.dto;

import com.project.barter.board.Board;
import com.project.barter.user.User;
import com.project.barter.user.dto.UserInfo;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class BoardResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime writeTime;

    private UserInfo user;

    public static BoardResponse byBoard(Board board){
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writeTime(board.getCreateDate())
                .user(UserInfo.byUser(board.getUser()))
                .build();
    }

}
