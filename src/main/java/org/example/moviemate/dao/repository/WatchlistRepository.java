package org.example.moviemate.dao.repository;

import org.example.moviemate.dao.entity.WatchlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<WatchlistEntity, LinkOption> {
    Optional<WatchlistEntity> findByUserId(Long userId);
}
