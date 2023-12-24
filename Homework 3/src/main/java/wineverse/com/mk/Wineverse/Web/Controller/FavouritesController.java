package wineverse.com.mk.Wineverse.Web.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wineverse.com.mk.Wineverse.Model.User;
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
    private List<Winery> getFavorites(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (userService.getUserByUsername(auth.getName()).isPresent()){
            User user = userService.getUserByUsername(auth.getName()).get();
            return wineryService.getWineriesByIds(user.getFavorites());
        }
        return new ArrayList<>();
    }

    private List<String> getFavoritesAsString(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (userService.getUserByUsername(auth.getName()).isPresent()){
            User user = userService.getUserByUsername(auth.getName()).get();
            return wineryService.getFavoritesAsString(user.getFavorites());
        }
        return new ArrayList<>();
    }

    @GetMapping("/favorites")
    public String getFavorites(Model model) {
        setCitiesAttribute(model);
        model.addAttribute("wineries", getFavorites());
        return "Favourites";
    }

    @GetMapping("/favorites/map")
    public String showWineriesMap(Model model){
        setCitiesAttribute(model);
        model.addAttribute("favoritesList", getFavoritesAsString());
        return "FavouritesMap";
    }

    @PostMapping("/change-favorite")
    public String changeFavorite(HttpServletRequest request, @RequestParam(name = "wineryId") Long id){
        userService.alterFavorites(id);
        return String.format("redirect:%s", request.getHeader("referer"));
    }
}
