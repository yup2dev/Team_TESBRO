package com.team.tesbro.Academy;

import com.team.tesbro.Board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Integer> {

    List<Academy> findByAcademyNameContaining(String keyword);

    Page<Academy> findByAcademyName(String academyName, Pageable pageable);

    @Query(value = "SELECT * FROM ACADEMY ORDER BY JJIM DESC LIMIT 5", nativeQuery = true)
    List<Academy> findMostjjimAcademy();
}

