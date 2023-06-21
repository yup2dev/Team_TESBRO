package com.team.tesbro.Review;

import com.team.tesbro.Academy.Academy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findAllByAcademy(Academy academy, Pageable pageable);

}
