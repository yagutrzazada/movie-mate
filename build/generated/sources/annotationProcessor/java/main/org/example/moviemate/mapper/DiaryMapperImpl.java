package org.example.moviemate.mapper;

import javax.annotation.processing.Generated;
import org.example.moviemate.dao.entity.DiaryEntity;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.model.dto.DiaryDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-20T14:11:39+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class DiaryMapperImpl implements DiaryMapper {

    @Override
    public DiaryDTO toDiaryDTO(DiaryEntity diaryEntity) {
        if ( diaryEntity == null ) {
            return null;
        }

        DiaryDTO diaryDTO = new DiaryDTO();

        diaryDTO.setUserId( diaryEntityUserId( diaryEntity ) );
        diaryDTO.setWatchedAt( diaryEntity.getWatchedAt() );

        diaryDTO.setMovies( mapMoviesToTitles(diaryEntity.getMovies()) );

        return diaryDTO;
    }

    @Override
    public DiaryEntity toDiaryEntity(DiaryDTO diaryDTO) {
        if ( diaryDTO == null ) {
            return null;
        }

        DiaryEntity.DiaryEntityBuilder diaryEntity = DiaryEntity.builder();

        diaryEntity.watchedAt( diaryDTO.getWatchedAt() );

        return diaryEntity.build();
    }

    private Long diaryEntityUserId(DiaryEntity diaryEntity) {
        if ( diaryEntity == null ) {
            return null;
        }
        UserEntity user = diaryEntity.getUser();
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
