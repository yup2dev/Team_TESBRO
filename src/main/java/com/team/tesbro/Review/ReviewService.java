package com.team.tesbro.Review;

import com.team.tesbro.Academy.Academy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void create(Academy academy, String content, int star_rating){
        Review review = new Review();
        review.setContent(content);
        review.setCreateDate(LocalDateTime.now());
        review.setModifyDate(LocalDateTime.now());
        review.setStar_rating(star_rating);
        review.setUserId("testid");
        review.setAcademy(academy);
        this.reviewRepository.save(review);
    }
}
