package wineverse.com.mk.Wineverse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wineverse.com.mk.Wineverse.Repository.CityRepository;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.model.City;
import wineverse.com.mk.Wineverse.model.Winery;

import java.util.List;

@Controller
public class WineryController {
    private CityService cityService;
    private WineryService wineryService;

    public WineryController(CityService cityService, WineryService wineryService) {
        this.cityService = cityService;
        this.wineryService = wineryService;
    }

    @GetMapping("/wineries")
    public String get_results_mapping(Model model)
    {
        model.addAttribute("wineries", wineryService.all_wineries());
        return "Result";
    }
    @PostMapping("/results")
     public String post_results_mapping(@RequestParam(name="name") String winery_name, @RequestParam(name = "rating") int rating,
    @RequestParam(name = "distance") float distance, @RequestParam(name = "location") String city_name, Model model)
    {
        City city = cityService.find_city(city_name);
        List<Winery> filtered_wineries = wineryService.filtered_wineries(winery_name, rating, distance, city);
        model.addAttribute("wineries", filtered_wineries);
        return "Result";
    }
}
