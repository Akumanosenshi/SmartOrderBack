package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.UserEtyMapper;

import java.util.UUID;

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
    public User findUser(String email) {
        return userRepositoryJPA.findByEmail(email)
                .map(userEtyMapper::toModel)
                .orElse(null);
    }

    @Override
    public void deleteUser(UUID uuid) {
        userRepositoryJPA.findById(uuid).ifPresent(userRepositoryJPA::delete);
    }
}
