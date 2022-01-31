package com.project.barter.comment;

import com.project.barter.board.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment,Long> {

    @EntityGraph(attributePaths= {"board","subCommentList"})
    List<Comment> findAllByBoard (@Param("boardId") Board board);

    @Override
    @EntityGraph(attributePaths= {"board","subCommentList"})
    Optional<Comment> findById(Long aLong);
}
