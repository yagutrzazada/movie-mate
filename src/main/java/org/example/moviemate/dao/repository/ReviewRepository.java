package org.example.moviemate.dao.repository;

import org.example.moviemate.dao.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    //en son rey
    Optional<ReviewEntity> findTopByUserIdAndMovieIdOrderByCreatedAtDesc(Long userId, Long movieId);

    List<ReviewEntity> findAllByMovieId(Long movieId);

    //1 ildeki rey
    long countByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);
}
