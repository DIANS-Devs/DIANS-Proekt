package wineverse.com.mk.Wineverse.Service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Model.User;
import wineverse.com.mk.Wineverse.Repository.UserRepository;
import wineverse.com.mk.Wineverse.Service.UserService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void alterFavorites(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getUserByUsername(authentication.getName()).get();
        user.changeFavorite(id);
        userRepository.save(user);
    }
}
