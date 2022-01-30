package com.project.barter.board;

import com.project.barter.CommonField;
import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class BoardField extends CommonField {


    public static FieldDescriptor[] commentListResponseField(){
        FieldDescriptor[] commentListFields = new FieldDescriptor[]{
                fieldWithPath("commentList.[]").description("게시물 댓글 리스트"),
                fieldWithPath("commentList.[].id").description("댓글 식별자"),
                fieldWithPath("commentList.[].content").description("댓글 내용"),
                fieldWithPath("commentList.[].writeTime").description("댓글 작성 시간"),
                fieldWithPath("commentList.[].writerLoginId").description("댓글 작성 유저 로그인 아이디"),
                fieldWithPath("commentList.[].subCommentList.[]").description("대댓글 리스트"),
                fieldWithPath("commentList.[].subCommentList.[].id").description("대댓글 식별자"),
                fieldWithPath("commentList.[].subCommentList.[].content").description("대댓글 내용"),
                fieldWithPath("commentList.[].subCommentList.[].writerLoginId").description("대댓글 작성 유저 로그인 아이디"),
                fieldWithPath("commentList.[].subCommentList.[].writeTime").description("대댓글 작성 시간")
        };
        return commentListFields;
    }

    public static FieldDescriptor[] boardResponseField(){
        FieldDescriptor[] commentListFields = new FieldDescriptor[]{
                fieldWithPath("id").description("게시물 식별자"),
                fieldWithPath("title").description("게시물 제목"),
                fieldWithPath("content").description("게시물 내용"),
                fieldWithPath("writeTime").description("게시물 작성 시간"),
                fieldWithPath("writer.id").description("유저 식별자"),
                fieldWithPath("writer.writerLoginId").description("유저 로그인 아이디"),
                fieldWithPath("writer.name").description("유저 이름")
        };
        return commentListFields;
    }

    public static FieldDescriptor[] boardPreviewResponseField(){
        FieldDescriptor[] commentListFields = new FieldDescriptor[]{
                fieldWithPath("id").description("게시물 식별자"),
                fieldWithPath("title").description("게시물 제목"),
                fieldWithPath("writeTime").description("게시물 작성 시간"),
                fieldWithPath("writerLoginId").description("게시물 작성자 로그인 아이디")};
        return commentListFields;
    }


}
