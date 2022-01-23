package com.project.barter.board;

import com.project.barter.global.BaseTimeEntity;
import com.project.barter.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter @Setter
@Entity
public class Board extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public void addUser(User user){
        this.user = user;
        user.getBoardList().add(this);
    }

}
