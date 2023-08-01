package com.team.tesbro.review;

import com.team.tesbro.academy.Academy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findAllByAcademy(Academy academy, Pageable pageable);


    @Query("SELECT r FROM Review r ORDER BY r.createDate DESC LIMIT 4")
    List<Review> find4RecentReviews();

    List<Review> findByUserId(String name);

}
