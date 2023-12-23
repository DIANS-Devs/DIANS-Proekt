package wineverse.com.mk.Wineverse.Web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.WineryService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FavouritesController {
    private final WineryService wineryService;
    private final CityService cityService;

    public FavouritesController(WineryService wineryService, CityService cityService) {
        this.wineryService = wineryService;
        this.cityService = cityService;
    }

    private void setCitiesAttribute(Model model){
        model.addAttribute("cities", cityService.getAllCities());
    }

    @GetMapping("/favorites/map")
    public String showFavouritesMap(Model model){
        setCitiesAttribute(model);
        model.addAttribute("wineriesList", wineryService.getFavouriteWineriesAsString());
        return "FavouritesMap";
    }

    @GetMapping("/favorites")
    public String getFavorites(Model model, @CookieValue(value = "favorites", required = false) String favorites) {
//        setCitiesAttribute(model);
        List<Winery> wineries = new ArrayList<>();
        System.out.println(favorites);

        model.addAttribute("wineries", wineries);
        return "Favourites";
    }
}
