package wineverse.com.mk.Wineverse.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
@RequestMapping("/wineries")
public class WineryController {
    private final CityService cityService;
    private final WineryService wineryService;

    @GetMapping()
    public String getResultsMapping(Model model, HttpSession session) {
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("wineries", wineryService.getAllWineries());
        session.removeAttribute("wineryList");
        return "Wineries";
    }
    @PostMapping()
    public String postResultsMapping(@RequestParam(name="name", required = false) String wineryName,
                                        @RequestParam(name = "rating", required = false) Float wineryRating,
                                        @RequestParam(name = "distance", required = false) Float wineryDistance,
                                        @RequestParam(name = "location", required = false) String wineryCityName,
                                        Model model, HttpSession session) {
        //TODO SHOULD BE MODIFIED TO ID, NOT BY NAME
        City city = cityService.findCity(wineryCityName);
        model.addAttribute("cities", cityService.getAllCities());

        List<Winery> wineryList = (List<Winery>) session.getAttribute("wineryList");
        if(wineryList != null && wineryName == null){
            model.addAttribute("wineries", wineryList);
            return "Wineries";
        }

        List<Winery> filtered_wineries = wineryService.filteredWineries(wineryName, wineryRating, wineryDistance, city);
        model.addAttribute("searchName", wineryName);
        model.addAttribute("searchRating", wineryRating);
        model.addAttribute("searchDistance", wineryDistance);
        model.addAttribute("searchCity", city.getName());
        model.addAttribute("wineries", filtered_wineries);

        session.setAttribute("wineryList", filtered_wineries);

        return "Wineries";
    }

    @GetMapping("/{id}")
    public String getWineryById(@PathVariable Long id, Model model) {
        //TODO ADD ERROR
        Winery winery = wineryService.getWineryById(id).get();
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("winery", winery);

        List<Review> reviews = winery.getReviews();
        model.addAttribute("reviews", reviews);
        return "WineryDetails";
    }

    @GetMapping("/map")
    public String showWineriesMap(Model model, HttpSession session){
        List<String> wineries = wineryService.getWineriesAsString();
        model.addAttribute("wineriesList", wineries);
        model.addAttribute("cities", cityService.getAllCities());
        session.removeAttribute("wineryList");
        return "WineriesMap";
    }

    @PostMapping("/map")
    public String showFilteredWineriesMap(HttpSession session, Model model){
        List<Winery> wineryList = (List<Winery>) session.getAttribute("wineryList");
        model.addAttribute("cities", cityService.getAllCities());

        if(wineryList == null){
            List<String> wineries = wineryService.getWineriesAsString();
            model.addAttribute("wineriesList", wineries);
        }
        else{
          /*  //TODO SHOULD BE FIXED, NOT GOOD
            Pattern pattern = Pattern.compile("Winery\\(Id=(\\d+),");
            Matcher matcher = pattern.matcher(wineryList);

            List<Long> wineryIds = new ArrayList<>();

            while (matcher.find()) {
                Long wineryId = Long.parseLong(matcher.group(1));
                wineryIds.add(wineryId);
            }*/

            model.addAttribute("wineriesList",
//                    wineryService.findWineriesByIds(wineryIds)
                    wineryList
                    .stream()
                    .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                    .collect(Collectors.toList()));
        }
        return "WineriesMap";
    }
}
