package mk.com.wineverse.Controller;

import jakarta.servlet.http.HttpSession;
import mk.com.wineverse.HelpServices.StringManipulation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import mk.com.wineverse.Service.CityService;
import mk.com.wineverse.Service.WineryService;
import mk.com.wineverse.Model.City;
import mk.com.wineverse.Model.Winery;

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
