package com.project.barter.board.dto;

import com.project.barter.board.Board;
import com.project.barter.user.User;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class BoardPost {

    private String title;

    private String content;

    public Board toBoard(User user){
        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();
        board.addUser(user);
        return board;
    }

}
