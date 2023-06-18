package com.team.tesbro.Board;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByBoardCategory(String boardCategory, Pageable pageable);
    Page<Board> findBySubjectContainingIgnoreCase(String keyword, Pageable pageable);
}