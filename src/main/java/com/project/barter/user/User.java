package com.project.barter.user;

import com.project.barter.user.domain.Birthday;
import com.project.barter.user.validator.Birth;
import com.project.barter.user.validator.PhoneNumber;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
@Entity
public class User {
    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @Birth
    @Embedded
    private Birthday birthday;
    @Email
    private String email;
    @PhoneNumber
    private String phoneNumber;
}
