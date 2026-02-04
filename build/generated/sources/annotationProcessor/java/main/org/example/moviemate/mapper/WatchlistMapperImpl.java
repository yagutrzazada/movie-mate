package org.example.moviemate.mapper;

import javax.annotation.processing.Generated;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.dao.entity.WatchlistEntity;
import org.example.moviemate.model.dto.WatchlistDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-20T14:11:39+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class WatchlistMapperImpl implements WatchlistMapper {

    @Override
    public WatchlistEntity toWatchlistEntity(WatchlistDTO watchlistDTO) {
        if ( watchlistDTO == null ) {
            return null;
        }

        WatchlistEntity.WatchlistEntityBuilder watchlistEntity = WatchlistEntity.builder();

        watchlistEntity.user( map( watchlistDTO.getUserId() ) );

        watchlistEntity.movies( mapTitlesToMovies(watchlistDTO.getMovies()) );

        return watchlistEntity.build();
    }

    @Override
    public WatchlistDTO toWatchlistDTO(WatchlistEntity watchlistEntity) {
        if ( watchlistEntity == null ) {
            return null;
        }

        WatchlistDTO watchlistDTO = new WatchlistDTO();

        watchlistDTO.setUserId( watchlistEntityUserId( watchlistEntity ) );

        watchlistDTO.setMovies( mapMoviesToTitles(watchlistEntity.getMovies()) );

        return watchlistDTO;
    }

    private Long watchlistEntityUserId(WatchlistEntity watchlistEntity) {
        if ( watchlistEntity == null ) {
            return null;
        }
        UserEntity user = watchlistEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
