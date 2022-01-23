package com.project.barter.user.dto;

import com.project.barter.user.User;
import com.project.barter.user.domain.Birthday;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter @Setter
public class UserInfo {

    private Long id;
    private String loginId;
    private String password;
    private String name;
    private Birthday birthday;
    private String email;
    private String phoneNumber;

    public static UserInfo byUser(User user){
        return UserInfo.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
