package wineverse.com.mk.Wineverse.Config.Register.RegistrationService.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Config.Register.Exceptions.UserAlreadyExistException;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationModel.UserDto;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationService.IUserService;
import wineverse.com.mk.Wineverse.Model.User;
import wineverse.com.mk.Wineverse.Repository.UserRepository;

@Service
@Transactional
public class IUserServiceimpl implements IUserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
//        if (emailExists(userDto.getEmail())) {
//            throw new UserAlreadyExistException("There is an account with that email address: "
//                    + userDto.getEmail());
//        }
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setPhone_number(userDto.getPhonenumber());
        return repository.save(user);
    }
    private boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }
}
