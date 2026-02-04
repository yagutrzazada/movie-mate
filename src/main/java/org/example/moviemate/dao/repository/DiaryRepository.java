package org.example.moviemate.dao.repository;

import org.example.moviemate.dao.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    Optional<DiaryEntity> findByUserId(Long userId);

    @Query("SELECT COUNT(m) FROM DiaryEntity d JOIN d.movies m " +
            "WHERE d.user.id = :userId AND d.watchedAt >= :date")
    long countMoviesByUserIdAndWatchedAtAfter(@Param("userId") Long userId,
                                              @Param("date") LocalDateTime date);



    @Query("SELECT m.genre FROM DiaryEntity d JOIN d.movies m " +
            "WHERE d.user.id = :userId " +
            "GROUP BY m.genre " +
            "ORDER BY COUNT(m) DESC LIMIT 1")
    Optional<String> findFavoriteGenreByUserId(@Param("userId") Long userId);
}