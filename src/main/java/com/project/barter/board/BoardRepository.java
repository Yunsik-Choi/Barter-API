package com.project.barter.board;

import com.project.barter.board.dto.BoardPreview;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("SELECT new com.project.barter.board.dto.BoardPreview(b.id, b.title, b.createDate, u.loginId) FROM Board b JOIN b.user u")
    List<BoardPreview> findBoardPreviewAll();

    @Override
    @EntityGraph(attributePaths= {"commentList","user","commentList.subCommentList"})
    List<Board> findAll();

    @Override
    @EntityGraph(attributePaths= {"commentList","user","commentList.subCommentList"})
    Optional<Board> findById(Long id);
}
