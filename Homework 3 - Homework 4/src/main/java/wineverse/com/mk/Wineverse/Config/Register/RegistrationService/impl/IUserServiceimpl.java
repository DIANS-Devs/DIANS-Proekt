package wineverse.com.mk.Wineverse.Config.Register.RegistrationService.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationModel.UserDto;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationService.IUserService;
import wineverse.com.mk.Wineverse.Model.User;
import wineverse.com.mk.Wineverse.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IUserServiceimpl implements IUserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUserAccount(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
           return null; //return null if user with the certain email exists
        }
        if(usernameExists(userDto.getUsername())){
            return new User(); // return user but with all parameters null if username exists
        }
        String phoneNumber = "+389" + userDto.getPhoneNumber();
        if(phoneNumberExists(phoneNumber)){
            User user = new User();
            user.setPhoneNumber("exists");
            user.setUsername("notnull");
            return user;
        }
        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getName(), userDto.getLastname(),
                userDto.getEmail(), phoneNumber, new ArrayList<>());
        return repository.save(user);
    }
    public boolean emailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }
    public boolean usernameExists(String username) {
        return repository.findByUsername(username).isPresent();
    }
    public boolean phoneNumberExists(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
