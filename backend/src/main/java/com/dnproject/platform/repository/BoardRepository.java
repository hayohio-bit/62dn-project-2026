package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Board;
import com.dnproject.platform.domain.constant.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByType(BoardType type, Pageable pageable);
}
