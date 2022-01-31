package com.project.barter.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.barter.comment.Comment;
import com.project.barter.comment.SubComment.dto.SubCommentResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class CommentResponse {

    private Long id;

    private String content;

    private LocalDateTime writeTime;

    private String writerLoginId;

    private List<SubCommentResponse> subCommentList;

    public static List<CommentResponse> byCommentList(List<Comment> commentList) {
        return commentList.stream().map(comment -> CommentResponse.byComment(comment))
                .collect(Collectors.toList());
    }

    public static CommentResponse byComment(Comment comment){
        CommentResponse commentResponse = CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writeTime(comment.getCreateDate())
                .writerLoginId(comment.getWriterLoginId())
                .build();
        if(comment.getSubCommentList().size()==0)
            return commentResponse;
        commentResponse.setSubCommentList(SubCommentResponse.bySubCommentList(comment.getSubCommentList()));
        return commentResponse;
    }
}
