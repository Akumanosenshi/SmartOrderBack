package smartorder.alison.persistence.mappers;

import org.mapstruct.Mapper;
import smartorder.alison.domain.models.User;
import smartorder.alison.persistence.entities.UserEty;

@Mapper
public interface UserEtyMapper {

    UserEty toEty(final User user);

    User toModel(final UserEty userEty);
}
