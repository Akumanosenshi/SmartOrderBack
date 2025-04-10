package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.web.dtos.UserDto;

@Mapper
public interface UserDtoMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
