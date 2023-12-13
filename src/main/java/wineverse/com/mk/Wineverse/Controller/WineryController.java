package wineverse.com.mk.Wineverse.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import wineverse.com.mk.Wineverse.HelpServices.StringManipulation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@AllArgsConstructor
public class WineryController {
    private final CityService cityService;
    private final WineryService wineryService;

    @GetMapping("/wineries")
    public String getResultsMapping(Model model)
    {
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("wineries", wineryService.getAllWineries());
        return "Wineries";
    }
    @PostMapping("/wineries")
     public String postResultsMapping(@RequestParam(name="name") String winery_name,
                                        @RequestParam(name = "rating") int rating,
                                        @RequestParam(name = "distance") float distance,
                                        @RequestParam(name = "location") String city_name,
                                        Model model)
    {
        City city = cityService.findCity(city_name);
        List<Winery> filtered_wineries = wineryService.filteredWineries(winery_name, rating, distance, city);
        //model.addAttribute("city", city);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("wineries", filtered_wineries);
        return "Wineries";
    }

    @GetMapping("/wineries/{id}")
    public String getWineryById(@PathVariable Long id, Model model) {
        Winery winery = wineryService.getWineryById(id);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("winery", winery);

        List<Review> reviews = winery.getReviews();
        model.addAttribute("reviews", reviews);
        return "WineryDetails";
    }
}
