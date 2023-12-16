package wineverse.com.mk.Wineverse.Config.Register.RegistrationService;

import jakarta.jws.soap.SOAPBinding;
import wineverse.com.mk.Wineverse.Config.Register.Exceptions.UserAlreadyExistException;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationModel.UserDto;
import wineverse.com.mk.Wineverse.Model.User;

import java.util.List;

public interface IUserService {
    User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;
     boolean emailExists(String email);
     List<User> findAll();
    boolean phoneNumberExists(String phonenumber);
}
