package wineverse.com.mk.Wineverse.Config.Register.RegistrationService;

import wineverse.com.mk.Wineverse.Config.Register.RegistrationModel.UserDto;
import wineverse.com.mk.Wineverse.Model.User;

import java.util.List;

public interface IUserService {
    User registerNewUserAccount(UserDto userDto);
     boolean emailExists(String email);
     List<User> findAll();
    boolean phoneNumberExists(String phoneNumber);
}
