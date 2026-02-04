package org.example.moviemate.service.intf;

import org.example.moviemate.model.dto.DiaryDTO;
import java.util.List;

public interface DiaryService {
    void logMovie(Long movieId, String currentUsername);

    List<DiaryDTO> getUserDiary(Long targetUserId, String currentUsername);

    void removeMovieFromDiary(Long targetUserId, Long movieId, String currentUsername);
}