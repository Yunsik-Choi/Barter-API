package com.project.barter.user.dto;

import com.project.barter.user.domain.Birth;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class UserPost {

    private String userId;
    private String password;
    private String name;
    private Birth birth;
    private String email;
    private String phoneNumber;

}
