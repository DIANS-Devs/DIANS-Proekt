package mk.com.wineverse.Controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import mk.com.wineverse.Service.CityService;

@Controller
@AllArgsConstructor
public class HomeController {
    private final CityService cityService;
    @GetMapping("/")
    public String getHomeMapping(Model m){
        m.addAttribute("cities", cityService.getAllCities());
        return "Index";
    }
    @GetMapping("/about-us")
    public String getAboutUs() {
        return "AboutUs";
    }
}
