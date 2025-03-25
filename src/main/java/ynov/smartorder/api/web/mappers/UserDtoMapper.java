package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import smartorder.alison_api.web.dtos.UserDto;
import ynov.smartorder.api.domain.models.User;

@Mapper
public interface UserDtoMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
