package com.project.barter.board.service;

import com.project.barter.board.Board;
import com.project.barter.board.dto.BoardPost;
import com.project.barter.board.dto.BoardPreview;
import com.project.barter.board.dto.BoardResponse;
import com.project.barter.board.dto.BoardWithComment;
import com.project.barter.comment.dto.CommentPost;

import java.util.List;

public interface BoardService {

    Board findById(Long id);

    Board save(BoardPost boardRequest, String sessionLoginIdAttribute);

    List<BoardPreview> findBoardPreviewAll();

    List<BoardResponse> findAll();

    Board addComment(Long id, CommentPost commentPost, String sessionLoginIdAttribute);

    BoardWithComment findBoardWithCommentById(Long id);

    BoardWithComment addSubComment(Long boardId, Long commentId, CommentPost commentPost, String sessionLoginIdAttribute);

}
