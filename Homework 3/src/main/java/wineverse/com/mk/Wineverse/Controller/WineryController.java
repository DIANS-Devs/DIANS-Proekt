package wineverse.com.mk.Wineverse.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Model.SearchQuery;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Service.WinerySorting;

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
//    private final WinerySorting winerySorting;

    private void setCitiesAttribute(Model model){
        model.addAttribute("cities", cityService.getAllCities());
    }

    private void setSearchAttributes(Model model, SearchQuery searchQuery) {
        model.addAttribute("searchName", searchQuery.getName());
        model.addAttribute("searchRating", searchQuery.getRating());
        model.addAttribute("searchDistance", searchQuery.getDistance());
        model.addAttribute("searchCity", searchQuery.getCity().getName());
        model.addAttribute("wineries", searchQuery.getWineries());
    }

    private void addSearchQueryAttribute(HttpSession session, SearchQuery searchQuery){
        session.setAttribute("searchQuery", searchQuery);
    }

    private void removeSearchQueryAttribute(HttpSession session){
        session.removeAttribute("searchQuery");
    };

    @GetMapping()
    public String getResultsMapping(Model model, HttpSession session) {
        setCitiesAttribute(model);
        model.addAttribute("wineries", wineryService.getAllWineries());
        removeSearchQueryAttribute(session);
//        session.removeAttribute("wineryList");
        return "Wineries";
    }
    @PostMapping()
    public String postResultsMapping(@RequestParam(name="name", required = false) String wineryName,
                                        @RequestParam(name = "rating", required = false) Float wineryRating,
                                        @RequestParam(name = "distance", required = false) Float wineryDistance,
                                        @RequestParam(name = "location", required = false) String wineryCityName,
                                        Model model, HttpSession session) {
        //TODO SHOULD BE MODIFIED TO ID, NOT BY NAME
        setCitiesAttribute(model);
        SearchQuery retrievedQuery = (SearchQuery) session.getAttribute("searchQuery");
//        List<Winery> wineryList = (List<Winery>) session.getAttribute("wineryList");
        if(retrievedQuery != null && wineryName == null){
//            retrievedQuery.setWineries(winerySorting.sortWineriesByStatus(retrievedQuery.getWineries()));
            setSearchAttributes(model, retrievedQuery);
            return "Wineries";
        }

        City wineryCity = cityService.findCity(wineryCityName);
        List<Winery> filtered_wineries = wineryService.filteredWineries(wineryName, wineryRating, wineryDistance, wineryCity);

        SearchQuery searchQuery = new SearchQuery(wineryName, wineryRating, wineryDistance, wineryCity, filtered_wineries);
        setSearchAttributes(model, searchQuery);
        addSearchQueryAttribute(session, searchQuery);

        return "Wineries";
    }

    @GetMapping("/{id}")
    public String getWineryById(@PathVariable Long id, Model model) {
        //TODO ADD ERROR
        Winery winery = wineryService.getWineryById(id).get();
        setCitiesAttribute(model);
        model.addAttribute("winery", winery);

        List<Review> reviews = winery.getReviews();
        model.addAttribute("reviews", reviews);
        return "WineryDetails";
    }

    @GetMapping("/map")
    public String showWineriesMap(Model model, HttpSession session){
        List<String> wineries = wineryService.getWineriesAsString();
        model.addAttribute("wineriesList", wineries);
        setCitiesAttribute(model);

        session.removeAttribute("wineryList");

        removeSearchQueryAttribute(session);

        return "WineriesMap";
    }

    @PostMapping("/map")
    public String showFilteredWineriesMap(HttpSession session, Model model){
//        List<Winery> wineryList = (List<Winery>) session.getAttribute("wineryList");
        SearchQuery retrievedQuery = (SearchQuery) session.getAttribute("searchQuery");
        setCitiesAttribute(model);

        if(retrievedQuery == null){
            List<String> wineries = wineryService.getWineriesAsString();
            model.addAttribute("wineriesList", wineries);
        }
        else{
            //TODO SHOULD BE FIXED, NOT GOOD
            model.addAttribute("wineriesList", retrievedQuery
                    .getWineries()
                    .stream()
                    .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                    .collect(Collectors.toList()));
        }
        return "WineriesMap";
    }
}
