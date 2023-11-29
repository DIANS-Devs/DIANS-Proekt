package wineverse.com.mk.Wineverse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;
import wineverse.com.mk.Wineverse.Repository.CityRepository;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.model.City;
import wineverse.com.mk.Wineverse.model.Winery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class   WineryController {
    private CityService cityService;
    private WineryService wineryService;

    public WineryController(CityService cityService, WineryService wineryService) {
        this.cityService = cityService;
        this.wineryService = wineryService;
    }

    @GetMapping("/wineries")
    public String get_results_mapping(Model model)
    {
        model.addAttribute("cities", cityService.all_Cities());
        model.addAttribute("wineries", wineryService.all_wineries());
        return "Result";
    }
    @PostMapping("/wineries")
     public String post_results_mapping(@RequestParam(name="name") String winery_name, @RequestParam(name = "rating") int rating,
    @RequestParam(name = "distance") float distance, @RequestParam(name = "location") String city_name, Model model)
    {
        City city = cityService.find_city(city_name);
        List<Winery> filtered_wineries = wineryService.filtered_wineries(winery_name, rating, distance, city);
        //model.addAttribute("city", city);
        model.addAttribute("cities", cityService.all_Cities());
        model.addAttribute("wineries", filtered_wineries);
        return "Result";
    }

    @GetMapping("/wineries/{id}")
    public String getWineryById(@PathVariable Long id, Model model) {
        Winery winery = wineryService.getWineryById(id);
        model.addAttribute("cities", cityService.all_Cities());
        model.addAttribute("winery", winery);
        return "winery-details";
    }

    @PostMapping("/favorites-show")
    public String setCookieFromClient(@RequestBody String  cookieValue, RedirectAttributes redirectAttributes) {
        String cleanString = cookieValue.replace("[", "").replace("]", "").replace("\\\"", "");
        if(cleanString.length() > 2){
            cleanString = cleanString.substring(1, cleanString.length()-1);
        }
        String[] values = cleanString.split(",");
        redirectAttributes.addAttribute("stringIds", Arrays.asList(values));
        return "redirect:/favorites-show";
    }

    @GetMapping("/favorites-show")
    public String getFavoritesShow(@RequestParam(name = "stringIds", required = false) List<String> stringIds, Model model) {
        model.addAttribute("favoriteWineries", wineryService.getWineriesByIds(stringIds));
        return "favorites-show";
    }
    @GetMapping("/favorites")
    public String getFavorites() {
        return "favorites";
    }

}
