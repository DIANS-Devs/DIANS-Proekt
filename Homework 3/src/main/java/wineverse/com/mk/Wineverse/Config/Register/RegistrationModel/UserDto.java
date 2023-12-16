package wineverse.com.mk.Wineverse.Config.Register.RegistrationModel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wineverse.com.mk.Wineverse.Config.Register.EmailValidation.ValidEmail;
import wineverse.com.mk.Wineverse.Config.Register.PasswordValidation.PasswordMatches;

@Data
@PasswordMatches
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String lastname;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;


    @NotNull
    @NotEmpty
    private String phonenumber;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

}

