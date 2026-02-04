package org.example.moviemate.service.intf;

import org.example.moviemate.model.dto.WatchlistDTO;

public interface WatchlistService {
    void addMovieToWatchlist(Long targetUserId, Long movieId, String currentUsername);
    void removeMovieFromWatchlist(Long targetUserId, Long movieId, String currentUsername);
    WatchlistDTO getUserWatchlist(Long targetUserId, String currentUsername);
}