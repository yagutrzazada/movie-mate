package org.example.moviemate.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviemate.dao.entity.DiaryEntity;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.repository.DiaryRepository;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.dao.repository.UserRepository;
import org.example.moviemate.exception.NotFoundException;
import org.example.moviemate.mapper.DiaryMapper;
import org.example.moviemate.model.dto.DiaryDTO;
import org.example.moviemate.model.enums.Role;
import org.example.moviemate.service.intf.DiaryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryMapper diaryMapper;
    private final DiaryRepository diaryRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void logMovie(Long movieId, String currentUsername) {
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı: " + currentUsername));

        log.info("ActionLog.logMovie.start - userId: {}, movieId: {}", user.getId(), movieId);

        DiaryEntity diary = diaryRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    DiaryEntity newDiary = new DiaryEntity();
                    newDiary.setUser(user);
                    newDiary.setMovies(new ArrayList<>());
                    newDiary.setWatchedAt(LocalDateTime.now());
                    return diaryRepository.save(newDiary);
                });

        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Film tapılmadı: " + movieId));

        if (diary.getMovies().contains(movie)) {
            throw new RuntimeException("Bu film artıq gündəliyinizdə mövcuddur!");
        }

        diary.getMovies().add(movie);
        diary.setWatchedAt(LocalDateTime.now());
        diaryRepository.save(diary);

        log.info("ActionLog.logMovie.success - Film istifadəçinin öz gündəliyinə əlavə edildi");
    }

    @Override
    public List<DiaryDTO> getUserDiary(Long targetUserId, String currentUsername) {
        Long finalUserId = determineTargetUserId(targetUserId, currentUsername);
        return diaryRepository.findByUserId(finalUserId)
                .stream()
                .map(diaryMapper::toDiaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeMovieFromDiary(Long targetUserId, Long movieId, String currentUsername) {
        Long finalUserId = determineTargetUserId(targetUserId, currentUsername);

        DiaryEntity diary = diaryRepository.findByUserId(finalUserId)
                .orElseThrow(() -> new NotFoundException("Gündəlik tapılmadı"));

        boolean removed = diary.getMovies().removeIf(movie -> movie.getId().equals(movieId));

        if (!removed) {
            throw new NotFoundException("Bu film gündəliyinizdə tapılmadı");
        }

        diary.setWatchedAt(LocalDateTime.now());
        diaryRepository.save(diary);
        log.info("ActionLog.removeFromDiary.success - Film siyahıdan silindi.");
    }

    private Long determineTargetUserId(Long targetUserId, String currentUsername) {
        UserEntity currentUser = userRepository.findByUsername(currentUsername)
                .or(() -> userRepository.findByEmail(currentUsername))
                .orElseThrow(() -> new NotFoundException("İstifadəçi tapılmadı"));

        if (targetUserId != null && currentUser.getRole() == Role.ADMIN) {
            return targetUserId;
        }
        return currentUser.getId();
    }
}