package org.example.moviemate.mapper;

import javax.annotation.processing.Generated;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.dao.entity.ReviewEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.model.dto.ReviewDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-20T14:11:39+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewEntity toReviewEntity(ReviewDTO reviewDTO) {
        if ( reviewDTO == null ) {
            return null;
        }

        ReviewEntity.ReviewEntityBuilder reviewEntity = ReviewEntity.builder();

        reviewEntity.user( mapUserIdToUser( reviewDTO.getUserId() ) );
        reviewEntity.movie( mapMovieIdToMovie( reviewDTO.getMovieId() ) );
        reviewEntity.id( reviewDTO.getId() );
        reviewEntity.rating( reviewDTO.getRating() );
        reviewEntity.comment( reviewDTO.getComment() );

        return reviewEntity.build();
    }

    @Override
    public ReviewDTO toReviewDTO(ReviewEntity reviewEntity) {
        if ( reviewEntity == null ) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setUserId( reviewEntityUserId( reviewEntity ) );
        reviewDTO.setMovieId( reviewEntityMovieId( reviewEntity ) );
        reviewDTO.setId( reviewEntity.getId() );
        reviewDTO.setRating( reviewEntity.getRating() );
        reviewDTO.setComment( reviewEntity.getComment() );

        return reviewDTO;
    }

    private Long reviewEntityUserId(ReviewEntity reviewEntity) {
        if ( reviewEntity == null ) {
            return null;
        }
        UserEntity user = reviewEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long reviewEntityMovieId(ReviewEntity reviewEntity) {
        if ( reviewEntity == null ) {
            return null;
        }
        MovieEntity movie = reviewEntity.getMovie();
        if ( movie == null ) {
            return null;
        }
        Long id = movie.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
