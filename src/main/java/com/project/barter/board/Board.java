package com.project.barter.board;

import com.project.barter.global.BaseTimeEntity;
import com.project.barter.user.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter @Setter
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_WRITER_USER_ID")
    private User user;

    public void addWriter(User user){
        this.user = user;
        user.getBoardList().add(this);
    }

}
