package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.ReviewEntity;
import org.example.moviemate.model.dto.ReviewDTO;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.entity.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "movie", source = "movieId")
    ReviewEntity toReviewEntity(ReviewDTO reviewDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "movieId", source = "movie.id")
    ReviewDTO toReviewDTO(ReviewEntity reviewEntity);

    default UserEntity mapUserIdToUser(Long userId) {
        if (userId != null) {
            UserEntity user = new UserEntity();
            user.setId(userId);
            return user;
        }
        return null;
    }

    default MovieEntity mapMovieIdToMovie(Long movieId) {
        if (movieId != null) {
            MovieEntity movie = new MovieEntity();
            movie.setId(movieId);
            return movie;
        }
        return null;
    }
}