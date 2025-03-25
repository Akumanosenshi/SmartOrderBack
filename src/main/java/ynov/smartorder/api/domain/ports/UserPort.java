package ynov.smartorder.api.domain.ports;


import ynov.smartorder.api.domain.models.User;

public interface UserPort {
    void saveUser(User user);

    void deleteUser(User user);
}
