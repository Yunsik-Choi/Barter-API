package com.project.barter.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter @Setter
public class UserLogin {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String password;

    public UserLogin(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
