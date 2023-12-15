package wineverse.com.mk.Wineverse.Config.Register.RegistrationService;

import wineverse.com.mk.Wineverse.Config.Register.Exceptions.UserAlreadyExistException;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationModel.UserDto;
import wineverse.com.mk.Wineverse.Model.User;

public interface IUserService {
    User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;
}
