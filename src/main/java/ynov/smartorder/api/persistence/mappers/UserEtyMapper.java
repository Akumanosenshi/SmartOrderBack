package ynov.smartorder.api.persistence.mappers;

import org.mapstruct.Mapper;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.persistence.entities.UserEty;

@Mapper
public interface UserEtyMapper {

    UserEty toEty(final User user);

    User toModel(final UserEty userEty);
}
