package org.example.moviemate.dao.repository;

import org.example.moviemate.dao.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findByTitleContainingIgnoreCase(String title);

    long countByCreatedAtAfter(LocalDateTime date);
}
