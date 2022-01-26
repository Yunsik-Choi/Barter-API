package com.project.barter.board;

import com.project.barter.comment.Comment;
import com.project.barter.global.BaseTimeEntity;
import com.project.barter.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name = "BOARD_ID")
    private final List<Comment> commentList = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    private User user;

    public void addUser(User user){
        this.user = user;
        user.getBoardList().add(this);
    }

}
