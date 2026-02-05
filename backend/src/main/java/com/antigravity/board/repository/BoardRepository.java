package com.antigravity.board.repository;

import com.antigravity.board.entity.Board;
import com.antigravity.board.entity.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByType(BoardType type, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.type = :type AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword% OR b.author LIKE %:keyword%)")
    Page<Board> searchByType(@Param("type") BoardType type, @Param("keyword") String keyword, Pageable pageable);
}
