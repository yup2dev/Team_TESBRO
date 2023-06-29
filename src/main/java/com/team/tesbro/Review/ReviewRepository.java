package com.team.tesbro.Review;

import com.team.tesbro.Academy.Academy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findAllByAcademy(Academy academy, Pageable pageable);


    @Query(value = "SELECT * FROM REVIEW ORDER BY CREATE_DATE DESC LIMIT 4", nativeQuery = true)
    List<Review> find4RecentReviews();

    List<Review> findByUserId(String name);

}
