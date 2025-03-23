package smartorder.alison.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smartorder.alison.domain.models.User;
import smartorder.alison.domain.ports.UserPort;
import smartorder.alison.persistence.mappers.UserEtyMapper;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPort {
    private final UserRepositoryJPA userRepositoryJPA;
    private final UserEtyMapper userEtyMapper;
    @Override
    public void saveUser(User user) {
        userRepositoryJPA.save(user);
    }
    @Override
    public void deleteUser(User user) {
        userRepositoryJPA.findById(user.getId()).ifPresent(userRepositoryJPA::delete);
    }
}
