package com.vtluan.place_order_football.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Review;
import com.vtluan.place_order_football.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review saveReview(Review review) {
        return this.reviewRepository.save(review);
    }

    public List<Review> getReviewsByStars(long star) {
        return this.reviewRepository.findByStars(star);
    }

    public List<Review> getAllReview() {
        return this.reviewRepository.findAll();
    }
}
