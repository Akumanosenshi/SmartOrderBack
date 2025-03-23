package smartorder.alison.domain.ports;

import smartorder.alison.domain.models.User;

public interface UserPort {
    void saveUser(User user);

    void deleteUser(User user);
}
