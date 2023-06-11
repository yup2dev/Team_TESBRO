package com.ll.basic1.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByBoardCategory(String boardCategory, Pageable pageable);
    Page<Board> findBySubjectContainingIgnoreCase(String keyword, Pageable pageable);
}