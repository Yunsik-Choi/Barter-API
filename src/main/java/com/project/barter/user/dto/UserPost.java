package com.project.barter.user.dto;

import com.project.barter.user.validator.Birth;
import com.project.barter.user.validator.PhoneNumber;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class UserPost {

    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @Birth
    private LocalDate birthday;
    @Email
    private String email;
    @PhoneNumber
    private String phoneNumber;

}
