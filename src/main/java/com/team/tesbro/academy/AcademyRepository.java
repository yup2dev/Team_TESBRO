package com.team.tesbro.academy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Integer> {



//    Page<Academy> findByOrder(String order, Pageable pageable);

    List<Academy> findByAcademyNameContaining(String keyword);

    @Query("SELECT DISTINCT a FROM Academy a " +
            "LEFT JOIN a.lessonList l " +
            "WHERE ((:keyword IS NULL OR a.academyName LIKE %:keyword%) " +
            "AND (:localKey IS NULL OR a.academyAddress LIKE %:localKey%)) " +
            "AND (:capacityType IS NULL OR (:capacityType = 1 AND l.peopleCapacity = 1) " +
            "OR (:capacityType = 2 AND (l.peopleCapacity >= 2 AND l.peopleCapacity <= 4)) " +
            "OR (:capacityType = 3 AND (l.peopleCapacity >= 5 AND l.peopleCapacity <= 10)) " +
            "OR (:capacityType = 4 AND l.peopleCapacity >= 10))")
    Page<Academy> searchByC(
            @Param("keyword") String keyword,
            @Param("localKey") String localKey,
            @Param("capacityType") Integer capacityType,
            Pageable pageable);

    @Query("SELECT DISTINCT a FROM Academy a " +
            "LEFT JOIN a.lessonList l " +
            "WHERE ((:keyword IS NULL OR a.academyName LIKE %:keyword%) " +
            "AND (:localKey IS NULL OR a.academyAddress LIKE %:localKey%)) " +
            "AND ((:capacityType IS NULL) " +
            "OR (:capacityType = 1 AND (l IS NULL OR l.peopleCapacity = 1)) " +
            "OR (:capacityType = 2 AND (l IS NULL OR (l.peopleCapacity >= 2 AND l.peopleCapacity <= 4))) " +
            "OR (:capacityType = 3 AND (l IS NULL OR (l.peopleCapacity >= 5 AND l.peopleCapacity <= 10))) " +
            "OR (:capacityType = 4 AND (l IS NULL OR l.peopleCapacity >= 10)))")
    Page<Academy> searchByKL(
            @Param("keyword") String keyword,
            @Param("localKey") String localKey,
            @Param("capacityType") Integer capacityType,
            Pageable pageable);

    @Query("SELECT DISTINCT a FROM Academy a " +
            "JOIN a.lessonList l " +
            "WHERE a.academyName LIKE %:keyword% " +
            "AND a.academyAddress LIKE %:localKey% " +
            "AND ((:capacityType IS NULL) " +
            "OR (:capacityType = 1 AND l.peopleCapacity = 1) " +
            "OR (:capacityType = 2 AND l.peopleCapacity >= 2 AND l.peopleCapacity <= 4) " +
            "OR (:capacityType = 3 AND l.peopleCapacity >= 5 AND l.peopleCapacity <= 10) " +
            "OR (:capacityType = 4 AND l.peopleCapacity >= 10))")
    Page<Academy> searchByKLC(
            @Param("keyword") String keyword,
            @Param("localKey") String localKey,
            @Param("capacityType") Integer capacityType,
            Pageable pageable);



    @Query("SELECT a FROM Academy a ORDER BY a.jjim DESC LIMIT 5")
    List<Academy> findMostJjimAcademy();

    @Query("SELECT a FROM Academy a ORDER BY a.createDate DESC LIMIT 5")
    List<Academy> findMostRecentlyAcademy();

    List<Academy> findByAcademyAddressContaining(String address);
}
