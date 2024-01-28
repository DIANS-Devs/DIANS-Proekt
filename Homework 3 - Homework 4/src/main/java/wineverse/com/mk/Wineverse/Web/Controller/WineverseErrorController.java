package wineverse.com.mk.Wineverse.Web.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WineverseErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(){
        return "Error";
    }
}
