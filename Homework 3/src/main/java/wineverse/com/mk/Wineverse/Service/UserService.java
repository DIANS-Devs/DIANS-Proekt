package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String username);
    void alterFavorites(Long id);
}
