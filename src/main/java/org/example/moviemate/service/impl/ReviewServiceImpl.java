package org.example.moviemate.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.ReviewEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.dao.repository.ReviewRepository;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.exception.NotFoundException;
import org.example.moviemate.mapper.ReviewMapper;
import org.example.moviemate.model.dto.ReviewDTO;
import org.example.moviemate.model.enums.Role;
import org.example.moviemate.service.intf.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public void addReview(ReviewDTO reviewDTO) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı"));

        MovieEntity movie = movieRepository.findById(reviewDTO.getMovieId())
                .orElseThrow(() -> new NotFoundException("Film tapılmadı"));

        Optional<ReviewEntity> lastReviewOpt = reviewRepository.findTopByUserIdAndMovieIdOrderByCreatedAtDesc(user.getId(), movie.getId());

        Double finalRating;
        if (lastReviewOpt.isPresent()) {
            finalRating = (reviewDTO.getRating() != null && reviewDTO.getRating() > 0) ? reviewDTO.getRating() : lastReviewOpt.get().getRating();
        } else {
            if (reviewDTO.getRating() == null) throw new RuntimeException("Reytinq mütləqdir!");
            finalRating = reviewDTO.getRating();
        }

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setUser(user);
        reviewEntity.setMovie(movie);
        reviewEntity.setRating(finalRating);
        reviewEntity.setComment(reviewDTO.getComment());
        reviewEntity.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(reviewEntity);
        updateMovieAverageRating(movie);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, String currentUsername) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Rəy tapılmadı"));

        UserEntity currentUser = userRepository.findByUsername(currentUsername).get();

        if (!review.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Siz başqasının rəyini silə bilməzsiniz!");
        }

        MovieEntity movie = review.getMovie();
        reviewRepository.delete(review);
        updateMovieAverageRating(movie);
    }

    @Override
    public List<ReviewDTO> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findAllByMovieId(movieId).stream()
                .map(reviewMapper::toReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getUserReviewForMovie(Long movieId, String currentUsername) {
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı"));

        return reviewRepository.findTopByUserIdAndMovieIdOrderByCreatedAtDesc(user.getId(), movieId)
                .map(reviewMapper::toReviewDTO)
                .orElseThrow(() -> new NotFoundException("Bu filmə rəyiniz tapılmadı"));
    }

    private void updateMovieAverageRating(MovieEntity movie) {
        List<ReviewEntity> reviews = reviewRepository.findAllByMovieId(movie.getId());
        double average = reviews.stream().mapToDouble(ReviewEntity::getRating).average().orElse(0.0);
        movie.setAverageRating(Math.round(average * 10.0) / 10.0);
        movieRepository.save(movie);
    }
}