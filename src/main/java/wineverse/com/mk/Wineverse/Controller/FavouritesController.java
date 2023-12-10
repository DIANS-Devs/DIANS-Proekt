package wineverse.com.mk.Wineverse.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wineverse.com.mk.Wineverse.HelpServices.StringManipulation;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Service.WineryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class FavouritesController {
    private final WineryService wineryService;
    private final StringManipulation stringManipulation = new StringManipulation();

    public FavouritesController(WineryService wineryService) {
        this.wineryService = wineryService;
    }

    @GetMapping("/favourites/map")
    public String showFavouritesMap(Model model){
        model.addAttribute("wineriesList", wineryService.getFavouriteWineriesAsString());
        return "FavouritesMap";
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

    @PostMapping("/favorites-list")
    @ResponseBody
    public ResponseEntity<String> receiveFavoritesList(@RequestBody String favoritesList, HttpSession session) {
        session.setAttribute("processedFavoritesList", favoritesList);
        return ResponseEntity.ok("FavoritesList received successfully");
    }



}
