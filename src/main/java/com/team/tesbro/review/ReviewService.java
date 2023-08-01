package com.team.tesbro.review;

import com.team.tesbro.academy.Academy;
import com.team.tesbro.academy.AcademyService;
import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private AcademyService academyService;

    public Review create(Academy academy, String content, int star_rating, SiteUser author){
        Review review = new Review();
        review.setContent(content);
        review.setCreateDate(LocalDateTime.now());
        review.setModifyDate(LocalDateTime.now());
        review.setStar_rating(star_rating);
        review.setUserId(author);
        review.setAcademy(academy);
        this.reviewRepository.save(review);

        return review;
    }

    public Review getReview(Integer id) {
        Optional<Review> review = this.reviewRepository.findById(id);
        if(review.isPresent()) {
            return review.get();
        } else {
            throw new DataNotFoundException("review not found");
        }

    }

    public Page<Review> getList(Academy academy, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.reviewRepository.findAllByAcademy(academy, pageable);
    }


    public void modify(Review review, String content, int star_rating) {
        review.setContent(content);
        review.setStar_rating(star_rating);
        review.setModifyDate(LocalDateTime.now());
        this.reviewRepository.save(review);
    }


    public void delete(Review review) {
        this.reviewRepository.delete(review);
    }

    public void vote(Review review, SiteUser siteUser) {
        review.getVoter().add(siteUser);
        this.reviewRepository.save(review);
    }

    public long countReviewIds() {
        return reviewRepository.count();
    }

    public List<Review> get4RecentReviews() {
        return reviewRepository.find4RecentReviews();
    }

    public List<Review> getReviewByUserId(String name){
        return reviewRepository.findByUserId(name);
    }
}
