package wineverse.com.mk.Wineverse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wineverse.com.mk.Wineverse.Service.CityService;

@Controller
public class HomeController {

    private final CityService cityService;

    public HomeController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/")
    public String getHomeMapping(Model m){
        m.addAttribute("cities", cityService.all_Cities());
        return "Index";
    }
    @GetMapping("/about-us")
    public String getAboutUs() {
        return "AboutUs";
    }
}
