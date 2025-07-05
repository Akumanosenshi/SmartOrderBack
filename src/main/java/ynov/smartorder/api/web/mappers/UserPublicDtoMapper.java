package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.web.dtos.UserDto;
import ynov.smartorder.api.web.dtos.UserPublicDto;

@Mapper
public interface UserPublicDtoMapper {

        UserPublicDto toPublicDto(User user);

    User toUserDto(UserPublicDto userDto);
    }
