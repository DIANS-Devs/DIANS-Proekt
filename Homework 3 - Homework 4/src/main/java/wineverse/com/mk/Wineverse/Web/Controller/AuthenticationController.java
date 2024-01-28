package wineverse.com.mk.Wineverse.Web.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationModel.UserDto;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationService.IUserService;
import wineverse.com.mk.Wineverse.Model.User;

@Controller
public class AuthenticationController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String getLogInPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            //authenticationFacade.setAuthentication(authentication);
            return "redirect:/wineries";
        } else {
            return "LogIn";
        }
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/wineries";
        }
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        model.addAttribute("registeredUsers", userService.findAll());
        return "Registration";
    }

    @PostMapping("/user/registration")
    public String registerUserAccount(
            @ModelAttribute("user") @Valid UserDto userDto,
            Model model) {
        User registered = userService.registerNewUserAccount(userDto);
        if(registered == null){
            model.addAttribute("emailExists", true);
            return "Registration"; }
        else if(registered.getUsername() == null){
            model.addAttribute("usernameExists", true);
            return "Registration";
        }
        else if(registered.getPhoneNumber().equals("exists")){
            model.addAttribute("phoneExists", true);
            return "Registration";
        }

        return "redirect:/user/registration/success";
    }

    @GetMapping("/user/registration/success")
    public String successfulRegistration(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/wineries";
        }
        return "PostRegistration";
    }
}
