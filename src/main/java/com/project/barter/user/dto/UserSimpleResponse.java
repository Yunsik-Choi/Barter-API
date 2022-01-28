package com.project.barter.user.dto;

import com.project.barter.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSimpleResponse {

    private Long id;

    private String loginId;

    private String name;

    public UserSimpleResponse(Long id, String loginId, String name) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
    }

    public static UserSimpleResponse byUser(User user){
        return new UserSimpleResponse(user.getId(), user.getLoginId(), user.getName());
    }

}
