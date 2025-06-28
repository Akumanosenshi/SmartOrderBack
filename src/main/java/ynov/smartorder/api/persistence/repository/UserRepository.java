package ynov.smartorder.api.persistence.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.persistence.entities.UserEty;
import ynov.smartorder.api.persistence.mappers.UserEtyMapper;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPort {

    private final UserRepositoryJPA userRepositoryJPA;
    private final UserEtyMapper userEtyMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        String motDePasseHache = passwordEncoder.encode(user.getMdp());
        user.setMdp(motDePasseHache);
        userRepositoryJPA.save(userEtyMapper.toEty(user));
    }


    @Override
    public User findUser(String email, String password) {


        Optional<UserEty> userEty = userRepositoryJPA.findByEmail(email);

        if (userEty.isEmpty()) {

            return null;
        }

        User user = userEtyMapper.toModel(userEty.get());

        if (passwordEncoder.matches(password, user.getMdp())) {

            return user;
        } else {

            return null;
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepositoryJPA.findByEmail(email)
                .map(userEtyMapper::toModel);
    }

}
