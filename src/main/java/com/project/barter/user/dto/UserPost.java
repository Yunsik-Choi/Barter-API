package com.project.barter.user.dto;

import com.project.barter.user.domain.Birthday;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter @Setter
public class UserPost {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    private Birthday birthday;
    @Email
    private String email;
    @Size(min = 11,max = 11)
    private String phoneNumber;

}
