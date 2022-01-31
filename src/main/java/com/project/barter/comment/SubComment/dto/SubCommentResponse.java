package com.project.barter.comment.SubComment.dto;

import com.project.barter.comment.SubComment.SubComment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class SubCommentResponse {

    private Long id;

    private String writerLoginId;

    private String content;

    private LocalDateTime writeTime;

    public static List<SubCommentResponse> bySubCommentList(List<SubComment> subCommentList) {
        return subCommentList.stream()
                .map(subComment -> bySubComment(subComment))
                .collect(Collectors.toList());
    }

    private static SubCommentResponse bySubComment(SubComment subComment) {
        return new SubCommentResponse(
                subComment.getId(),
                subComment.getWriterLoginId(),
                subComment.getContent(),
                subComment.getCreateDate()
        );
    }
}
