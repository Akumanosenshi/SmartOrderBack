package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.persistence.mappers.UserEtyMapper;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPort {

    private final UserRepositoryJPA userRepositoryJPA;
    private final UserEtyMapper userEtyMapper;
    @Override
    public void saveUser(User user) {
        userRepositoryJPA.save(userEtyMapper.toEty(user));
    }
    @Override
    public void deleteUser(User user) {
        userRepositoryJPA.findById(user.getId()).ifPresent(userRepositoryJPA::delete);
    }
}
