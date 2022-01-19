package com.project.barter.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class UserLogin {

    private String userId;
    private String password;

    public UserLogin(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
