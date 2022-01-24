package com.project.barter.user;

import com.project.barter.user.dto.UserPost;

import java.time.LocalDate;

public class UserUtils {

    public static UserPost getCompleteUserPost(){
        return UserPost.builder()
                .loginId("loginId")
                .password("password")
                .name("이름")
                .birthday(LocalDate.of(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();
    }

    public static User getCompleteUser(){
        return User.builder()
                .loginId("loginId")
                .password("password")
                .name("이름")
                .birthday(LocalDate.of(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();
    }

    public static User getCompleteUser(Long id){
        return User.builder()
                .id(id)
                .loginId("loginId")
                .password("password")
                .name("이름")
                .birthday(LocalDate.of(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();
    }


}
