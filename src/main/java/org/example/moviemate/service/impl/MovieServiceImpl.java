package org.example.moviemate.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.repository.MovieRepository;
import org.example.moviemate.exception.NotFoundException;
import org.example.moviemate.mapper.MovieMapper;
import org.example.moviemate.model.dto.MovieDTO;
import org.example.moviemate.service.intf.MovieService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        log.info("ActionLog.createMovie.start - film adı: {}", movieDTO.getTitle());
        MovieEntity movieEntity = movieMapper.toMovieEntity(movieDTO);

        movieEntity.setCreatedAt(LocalDateTime.now());
        movieEntity.setAverageRating(0.0);

        MovieEntity savedMovie = movieRepository.save(movieEntity);

        log.info("ActionLog.createMovie.Success - film ID: {}", savedMovie.getId());
        return movieMapper.toMovieDTO(savedMovie);
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        log.info("ActionLog.getMovieById.start - id: {}", id);
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> {
            log.error("ActionLog.getMovieById.error - Film tapılmadı İD:{}", id);
            return new NotFoundException("Film tapılmadı! ID: " + id);
        });
        log.info("ActionLog.getMovieById.success - Film tapıldı: {}", movie.getTitle());
        return movieMapper.toMovieDTO(movie);

    }

    @Override
    public List<MovieDTO> getAllMovies() {
        log.info("ActionLog.getAllMovies.start");
        List<MovieDTO> movies = movieRepository.findAll().stream()
                .map(movieMapper::toMovieDTO)
                .collect(Collectors.toList());

        log.info("ActionLog.getAllMovies.end - tapılan say: {}", movies.size());
        return movies;
    }

    @Override
    @Transactional
    public MovieDTO updateMovie(Long movieId, MovieDTO movieDTO) {
        log.info("ActionLog.updateMovie.start - id: {}", movieId);
        MovieEntity movie = movieRepository.findById(movieId).
                orElseThrow(() -> new NotFoundException("Film tapılmadı: " + movieId));

        if (movieDTO.getTitle() != null && !movieDTO.getTitle().trim().isEmpty()) {
            movie.setTitle(movieDTO.getTitle());
        }

        if (movieDTO.getDescription() != null && !movieDTO.getDescription().trim().isEmpty()) {
            movie.setDescription(movieDTO.getDescription());
        }

        if (movieDTO.getDirector() != null && !movieDTO.getDirector().trim().isEmpty()) {
            movie.setDirector(movieDTO.getDirector());
        }

        if (movieDTO.getGenre() != null) {
            movie.setGenre(movieDTO.getGenre());
        }

        if (movieDTO.getReleaseDate() != null) {
            movie.setReleaseDate(movieDTO.getReleaseDate());
        }

        if (movieDTO.getActors() != null && !movieDTO.getActors().isEmpty()) {
            movie.setActors(movieDTO.getActors());
        }

        MovieEntity updatedMovie = movieRepository.save(movie);

        log.info("ActionLog.updateMovie.success - Film yeniləndi ID: {}", updatedMovie.getId());
        return movieMapper.toMovieDTO(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Silinəcək film tapılmadı ID: " + id);
        }
        movieRepository.deleteById(id);
        log.info("ActionLog.deleteMovie.success - Film silindi ID: {}", id);
    }

    @Override
    public List<MovieDTO> searchMovies(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(movieMapper::toMovieDTO)
                .collect(Collectors.toList());
    }
}


