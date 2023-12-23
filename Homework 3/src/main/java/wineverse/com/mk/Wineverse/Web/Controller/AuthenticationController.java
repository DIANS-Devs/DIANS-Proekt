package wineverse.com.mk.Wineverse.Web.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import wineverse.com.mk.Wineverse.Config.LogIn.AuthenticationFacade;
import wineverse.com.mk.Wineverse.Config.Register.Exceptions.UserAlreadyExistException;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationModel.UserDto;
import wineverse.com.mk.Wineverse.Config.Register.RegistrationService.IUserService;
import wineverse.com.mk.Wineverse.Model.User;

@Controller
public class AuthenticationController {
    @Autowired
    private AuthenticationFacade authenticationFacade;

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
    public String showRegistrationForm(WebRequest request, Model model) {
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
            HttpServletRequest request,
            Errors errors, Model model) {

        try {
            User registered = userService.registerNewUserAccount(userDto);
            if(registered == null){
                model.addAttribute("emailExists", true);
                return "Registration"; }
            else if(registered.getUsername() == null){
                model.addAttribute("usernameExists", true);
                return "Registration";
            }
            else if(registered.getPhonenumber().equals("exists")){
                model.addAttribute("phoneExists", true);
                return "Registration";
            }
        } catch (UserAlreadyExistException uaeEx) {
//            ModelAndView mav = new ModelAndView();
//            mav.addObject("message", "An account for that username/email already exists.");
//            return mav;
        }
        return "redirect:/user/registration/success";
    }

    @GetMapping("/user/registration/success")
    public String successfulRegistration(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/wineries";
        }
        return "PostRegistration";
    }
}
