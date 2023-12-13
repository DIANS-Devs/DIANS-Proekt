package wineverse.com.mk.Wineverse.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import wineverse.com.mk.Wineverse.Service.UserService;

@Controller
public class AuthorizationController {
    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogInPage(){
        return "LogIn";
    }
//    @PostMapping("/login")
//    public String postLogInPage(){
//        return "LogIn";
//    }
}
