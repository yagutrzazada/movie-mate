package org.example.moviemate.dao.repository;

import org.example.moviemate.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    long countByCreatedAtAfter(LocalDateTime date);
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
