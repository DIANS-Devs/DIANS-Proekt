package wineverse.com.mk.Wineverse.Web.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.UserService;
import wineverse.com.mk.Wineverse.Service.WineryService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FavouritesController {
    private final WineryService wineryService;
    private final CityService cityService;
    private final UserService userService;

    public FavouritesController(WineryService wineryService, CityService cityService, UserService userService) {
        this.wineryService = wineryService;
        this.cityService = cityService;
        this.userService = userService;
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

    @PostMapping("/change-favorite")
    public String changeFavorite(HttpServletRequest request, @RequestParam(name = "wineryId") Long id){
        userService.alterFavorites(id);
        return String.format("redirect:%s", request.getHeader("referer"));
    }
}
