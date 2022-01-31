package com.project.barter.comment.SubComment;

import com.project.barter.board.Board;
import com.project.barter.comment.Comment;
import com.project.barter.global.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Entity
public class SubComment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "SUBCOMMENT_ID")
    private Long id;

    @Column(name = "SUBCOMMENT_WRITER_USER_LOGINID")
    private String writerLoginId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_COMMENT_ID")
    private Comment parentComment;
}
