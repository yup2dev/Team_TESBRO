package com.team.tesbro.Review;

import com.team.tesbro.Academy.Academy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

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

    public Page<Review> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.reviewRepository.findAll(pageable);
    }
}
