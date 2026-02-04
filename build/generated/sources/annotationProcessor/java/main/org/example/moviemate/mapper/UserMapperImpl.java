package org.example.moviemate.mapper;

import javax.annotation.processing.Generated;
import org.example.moviemate.dao.entity.UserEntity;
import org.example.moviemate.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-20T14:11:39+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toUserEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.username( userDTO.getUsername() );
        userEntity.email( userDTO.getEmail() );

        return userEntity.build();
    }

    @Override
    public UserDTO toUserDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( userEntity.getId() );
        userDTO.setUsername( userEntity.getUsername() );
        userDTO.setEmail( userEntity.getEmail() );

        userDTO.setWatchlist( mapWatchlistToTitles(userEntity.getWatchlists()) );
        userDTO.setDiary( mapDiaryToTitles(userEntity.getDiaries()) );

        return userDTO;
    }
}
