package com.team.tesbro.Academy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Integer> {
    List<Academy> findByAcademyNameContaining(String keyword);

    @Query("SELECT a FROM Academy a WHERE (:keyword IS NULL OR a.academyName LIKE %:keyword% OR a.academyAddress LIKE %:keyword%)" +
            " AND (:localKey IS NULL OR a.academyName LIKE %:localKey% OR a.academyAddress LIKE %:localKey%)")
    Page<Academy> searchByAcademyNameOrAddress(@Param("keyword") String keyword,
                                               @Param("localKey") String localKey,
                                               Pageable pageable);


    @Query(value = "SELECT * FROM ACADEMY ORDER BY JJIM DESC LIMIT 5", nativeQuery = true)
    List<Academy> findMostjjimAcademy();
}

