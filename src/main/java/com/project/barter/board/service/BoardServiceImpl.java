package com.project.barter.board.service;

import com.project.barter.board.Board;
import com.project.barter.board.exception.BoardNotExistsException;
import com.project.barter.board.BoardRepository;
import com.project.barter.board.dto.BoardPost;
import com.project.barter.board.dto.BoardPreview;
import com.project.barter.board.dto.BoardResponse;
import com.project.barter.board.dto.BoardWithComment;
import com.project.barter.comment.Comment;
import com.project.barter.comment.exception.CommentNotExistsException;
import com.project.barter.comment.dto.CommentPost;
import com.project.barter.comment.CommentRepository;
import com.project.barter.comment.SubComment.SubComment;
import com.project.barter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public Board addComment(Long boardId, CommentPost commentPost, String loginId){
        Board board = this.findBoardById(boardId);
        Comment comment = commentRepository.save(commentPost.toComment(loginId, board));
        log.info("Save Comment {} Board {} Writer {}",comment.getId(),boardId,loginId);
        return board;
    }

    @Override
    public BoardWithComment findBoardWithCommentById(Long id) {
        Board board = this.findBoardById(id);
        List<Comment> boardCommentList = commentRepository.findAllByBoard(board);
        log.info("Find Board {} All Comment",id);
        return BoardWithComment.byBoard(board,boardCommentList);
    }

    @Override
    public BoardWithComment addSubComment(Long boardId, Long commentId, CommentPost commentPost, String sessionLoginIdAttribute) {
        Board board = this.findBoardById(boardId);
        Comment comment = findCommentById(commentId);
        SubComment subComment = commentPost.toSubComment(sessionLoginIdAttribute, comment);
        comment.addSubComment(subComment);
        log.info("Save Board {} Comment {} Sub Comment",boardId,commentId);
        List<Comment> boardCommentList = commentRepository.findAllByBoard(board);
        return BoardWithComment.byBoard(board,boardCommentList);
    }

    @Override
    public Board findBoardById(Long id){
        Optional<Board> findBoardById = boardRepository.findById(id);
        if(findBoardById.isEmpty())
            throw new BoardNotExistsException();
        log.info("Find Board {}",id);
        return findBoardById.get();
    }

    @Override
    public Board saveBoard(BoardPost boardPost, String sessionLoginIdAttribute) {
        Board saveRequestBoard = boardPost.toBoard(userService.findByLoginId(sessionLoginIdAttribute));
        Board board = boardRepository.save(saveRequestBoard);
        log.info("Board {} Writer {}",board.getId(),sessionLoginIdAttribute);
        return board;
    }

    @Override
    public List<BoardPreview> findBoardPreviewAll() {
        List<Board> boardList = boardRepository.findAll();
        log.info("Find All Boards Preview");
        return boardList.stream().map(board -> BoardPreview.byBoard(board)).collect(Collectors.toList());
    }

    @Override
    public List<BoardResponse> findAllBoards() {
        List<Board> boardList = boardRepository.findAll();
        log.info("Find All Boards");
        return boardList.stream().map(board -> BoardResponse.byBoard(board)).collect(Collectors.toList());
    }

    private Comment findCommentById(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if(findComment.isEmpty())
            throw new CommentNotExistsException();
        log.info("Find Comment {}",commentId);
        return findComment.get();
    }

}
