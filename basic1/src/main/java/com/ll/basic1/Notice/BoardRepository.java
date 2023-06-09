package com.ll.basic1.Notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Board findBySubject(String subject);
    Board findBySubjectAndContent(String subject, String content);
    List<Board> findBySubjectLike(String subject);
    Page<Board> findAll(Pageable pageable);
    Page<Board> findAll(Specification<Board> spec, Pageable pageable);
}
