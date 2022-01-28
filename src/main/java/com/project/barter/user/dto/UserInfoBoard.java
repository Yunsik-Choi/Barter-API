package com.project.barter.user.dto;

import com.project.barter.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInfoBoard {

    private Long id;

    private String writerLoginId;

    private String name;

    public UserInfoBoard(Long id, String writerLoginId, String name) {
        this.id = id;
        this.writerLoginId = writerLoginId;
        this.name = name;
    }

    public static UserInfoBoard byUser(User user){
        return new UserInfoBoard(user.getId(), user.getLoginId(), user.getName());
    }
}
