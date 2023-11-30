package wineverse.com.mk.Wineverse.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wineverse.com.mk.Wineverse.HelpServices.StringManipulation;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.model.City;
import wineverse.com.mk.Wineverse.model.Winery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class   WineryController {
    private final CityService cityService;
    private final WineryService wineryService;
    private final StringManipulation stringManipulation = new StringManipulation();

    public WineryController(CityService cityService, WineryService wineryService) {
        this.cityService = cityService;
        this.wineryService = wineryService;
    }

    @GetMapping("/wineries")
    public String get_results_mapping(Model model)
    {
        model.addAttribute("cities", cityService.all_Cities());
        model.addAttribute("wineries", wineryService.all_wineries());
        return "Wineries";
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
        return "Wineries";
    }

    @GetMapping("/wineries/{id}")
    public String getWineryById(@PathVariable Long id, Model model) {
        Winery winery = wineryService.getWineryById(id);
        model.addAttribute("cities", cityService.all_Cities());
        model.addAttribute("winery", winery);
        return "WineryDetails";
    }

    @PostMapping("/favorites-list")
    @ResponseBody
    public ResponseEntity<String> receiveFavoritesList(@RequestBody String favoritesList, HttpSession session) {
        session.setAttribute("processedFavoritesList", favoritesList);
        return ResponseEntity.ok("FavoritesList received successfully");
    }


    @GetMapping("/favorites")
    public String getFavorites(Model model, HttpSession session) {
        List<Winery> wineries = new ArrayList<>();
        String processedFavoritesList = (String) session.getAttribute("processedFavoritesList");
        if(processedFavoritesList != null) {
            String[] values = stringManipulation.parse_JSON(processedFavoritesList);
            if (values != null) {
                wineries = wineryService.getWineriesByIds(Arrays.asList(values));
            }
        }
        model.addAttribute("wineries", wineries);
        return "Favourites";
    }



}
