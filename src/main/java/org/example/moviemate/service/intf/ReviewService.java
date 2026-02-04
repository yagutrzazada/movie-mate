package org.example.moviemate.service.intf;

import org.example.moviemate.model.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    void addReview(ReviewDTO reviewDTO);

    void deleteReview(Long reviewId, String currentUsername);

    List<ReviewDTO> getReviewsByMovieId(Long movieId);

    ReviewDTO getUserReviewForMovie(Long movieId, String currentUsername);

}