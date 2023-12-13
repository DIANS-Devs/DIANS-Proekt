package wineverse.com.mk.Wineverse.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Repository.UserRepository;
import wineverse.com.mk.Wineverse.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
