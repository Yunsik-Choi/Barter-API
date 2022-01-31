package com.project.barter.comment;

import com.project.barter.board.Board;
import com.project.barter.comment.SubComment.SubComment;
import com.project.barter.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "COMMENT_WRITER_USER_LOGINID")
    private String writerLoginId;

    private String content;

    @ManyToOne
    @JoinColumn(name = "PARENT_BOARD_ID")
    private Board board;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private final List<SubComment> subCommentList = new ArrayList<>();

    public void addSubComment(SubComment subComment){
        this.subCommentList.add(subComment);
        subComment.setParentComment(this);
    }


}
