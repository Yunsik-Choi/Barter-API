package com.project.barter.user;

import com.project.barter.board.Board;
import com.project.barter.user.validator.PhoneNumber;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate birthday;
    @Email
    private String email;
    @PhoneNumber
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private final List<Board> boardList = new ArrayList<>();
}
