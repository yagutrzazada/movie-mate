package org.example.moviemate.service.intf;

import org.example.moviemate.model.dto.MovieDTO;

import java.util.List;

public interface MovieService {
    MovieDTO createMovie(MovieDTO movieDTO);

    void deleteMovie(Long id);

    MovieDTO updateMovie(Long movieId, MovieDTO movieDTO);

    MovieDTO getMovieById(Long id);

    List<MovieDTO> getAllMovies();

    List<MovieDTO> searchMovies(String title);
}
