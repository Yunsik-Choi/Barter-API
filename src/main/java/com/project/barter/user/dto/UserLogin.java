package com.project.barter.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter @Setter
public class UserLogin {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public UserLogin(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
