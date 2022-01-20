package com.project.barter.user;

import com.project.barter.user.domain.Birthday;
import com.project.barter.user.dto.UserPost;

public class UserUtils {

    public static UserPost getCompleteUserPost(){
        return UserPost.builder()
                .userId("userId")
                .password("password")
                .name("이름")
                .birthday(new Birthday(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();
    }

    public static User getCompleteUser(){
        return User.builder()
                .userId("userId")
                .password("password")
                .name("이름")
                .birthday(new Birthday(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();
    }


}
