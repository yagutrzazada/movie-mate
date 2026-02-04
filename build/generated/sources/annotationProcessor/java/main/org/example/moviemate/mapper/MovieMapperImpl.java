package org.example.moviemate.mapper;

import javax.annotation.processing.Generated;
import org.example.moviemate.dao.entity.MovieEntity;
import org.example.moviemate.model.dto.MovieDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-20T14:11:39+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public MovieEntity toMovieEntity(MovieDTO movieDTO) {
        if ( movieDTO == null ) {
            return null;
        }

        MovieEntity.MovieEntityBuilder movieEntity = MovieEntity.builder();

        movieEntity.id( movieDTO.getId() );
        movieEntity.title( movieDTO.getTitle() );
        movieEntity.genre( movieDTO.getGenre() );
        movieEntity.releaseDate( movieDTO.getReleaseDate() );
        movieEntity.description( movieDTO.getDescription() );
        movieEntity.averageRating( movieDTO.getAverageRating() );
        movieEntity.director( movieDTO.getDirector() );
        movieEntity.actors( movieDTO.getActors() );

        return movieEntity.build();
    }

    @Override
    public MovieDTO toMovieDTO(MovieEntity movieEntity) {
        if ( movieEntity == null ) {
            return null;
        }

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId( movieEntity.getId() );
        movieDTO.setTitle( movieEntity.getTitle() );
        movieDTO.setGenre( movieEntity.getGenre() );
        movieDTO.setReleaseDate( movieEntity.getReleaseDate() );
        movieDTO.setDescription( movieEntity.getDescription() );
        movieDTO.setAverageRating( movieEntity.getAverageRating() );
        movieDTO.setDirector( movieEntity.getDirector() );
        movieDTO.setActors( movieEntity.getActors() );

        return movieDTO;
    }
}
