package com.project.barter.board;

import com.project.barter.board.dto.BoardPost;
import com.project.barter.user.UserUtils;

public class BoardUtils {

    public static BoardPost getCompleteBoardPost(){
        return BoardPost.builder().title("제목").content("내용").build();
    }

    public static Board getCompleteBoard(){
        return Board.builder().title("title").content("content").user(UserUtils.getCompleteUser(1L)).build();
    }

}
