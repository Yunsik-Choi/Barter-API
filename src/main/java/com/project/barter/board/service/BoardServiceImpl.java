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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserService userService;

    @Override
    public Board addComment(Long id, CommentPost commentPost, String loginId){
        Board board = this.findById(id);
        commentRepository.save(commentPost.toComment(loginId,board));
        return board;
    }

    @Override
    public BoardWithComment findBoardWithCommentById(Long id) {
        Board board = this.findById(id);
        List<Comment> boardCommentList = commentRepository.findAllByBoard(board);
        return BoardWithComment.byBoard(board,boardCommentList);
    }

    @Override
    public BoardWithComment addSubComment(Long boardId, Long commentId, CommentPost commentPost, String sessionLoginIdAttribute) {
        Board board = this.findById(boardId);
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if(findComment.isEmpty())
            throw new CommentNotExistsException();
        Comment comment = findComment.get();
        SubComment subComment = commentPost.toSubComment(sessionLoginIdAttribute, comment);
        comment.addSubComment(subComment);
        List<Comment> boardCommentList = commentRepository.findAllByBoard(board);
        return BoardWithComment.byBoard(board,boardCommentList);
    }

    @Override
    public Board findById(Long id){
        Optional<Board> findBoardById = boardRepository.findById(id);
        if(findBoardById.isEmpty())
            throw new BoardNotExistsException();
        return findBoardById.get();
    }

    @Override
    public Board save(BoardPost boardPost, String sessionLoginIdAttribute) {
        Board board = boardPost.toBoard(userService.findByLoginId(sessionLoginIdAttribute));
        return boardRepository.save(board);
    }

    @Override
    public List<BoardPreview> findBoardPreviewAll() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream().map(board -> BoardPreview.byBoard(board)).collect(Collectors.toList());
    }

    @Override
    public List<BoardResponse> findAll() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream().map(board -> BoardResponse.byBoard(board)).collect(Collectors.toList());
    }

}
