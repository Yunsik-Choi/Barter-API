package com.project.barter.user.dto;

import com.project.barter.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponse {

    private Long id;

    private String loginId;

    private String name;

    public LoginResponse(Long id, String loginId, String name) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
    }

    public static LoginResponse byUser(User user){
        return new LoginResponse(user.getId(), user.getLoginId(), user.getName());
    }

}
