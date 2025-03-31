package ynov.smartorder.api.domain.ports;


import ynov.smartorder.api.domain.models.User;

import java.util.UUID;

public interface UserPort {
    void saveUser(User user);

    User findUser(String email);

    void deleteUser(UUID uuid);
}
