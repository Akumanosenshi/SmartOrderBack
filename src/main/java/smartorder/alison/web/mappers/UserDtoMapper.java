package smartorder.alison.web.mappers;

import org.mapstruct.Mapper;
import smartorder.alison.domain.models.User;
import smartorder.alison.web.dtos.UserDto;

@Mapper
public interface UserDtoMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
