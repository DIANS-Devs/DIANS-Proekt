package wineverse.com.mk.Wineverse.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wineverse.com.mk.Wineverse.Config.LogIn.UserInfoUserDetails;
import wineverse.com.mk.Wineverse.Config.LogIn.UserInfoUserDetailsService;
import wineverse.com.mk.Wineverse.Form.ReviewForm;
import wineverse.com.mk.Wineverse.Model.*;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Service.UserService;
import wineverse.com.mk.Wineverse.Service.WineryService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
@RequestMapping("/wineries")
public class WineryController {
    private final CityService cityService;
    private final WineryService wineryService;
    private final UserService userService;
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
        if(retrievedQuery != null && wineryName == null && wineryCityName == null && wineryRating == null && wineryDistance == null){
//            retrievedQuery.setWineries(winerySorting.sortWineriesByStatus(retrievedQuery.getWineries()));
            setSearchAttributes(model, retrievedQuery);
            return "Wineries";
        }
        // if everything is null, set to default values
        if(wineryName == null) {
            wineryName = "";
        }
        if(wineryRating == null){
            wineryRating = (float)0.0;
        }
        if(wineryDistance == null){
            wineryDistance = (float)300;
        }
        if(wineryCityName == null){
            wineryCityName = "Цела Македонија";
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
    @PostMapping("/submitReview")
    public String submitReview(@ModelAttribute ReviewForm reviewForm, Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserInfoUserDetails) {
                UserInfoUserDetails userDetails = (UserInfoUserDetails) principal;

                String userName = userDetails.getUsername();
                User user = userService.getUserByUsername(userName).stream().findFirst().get();

                Long wineryId = reviewForm.getWineryId();
                Integer rating = reviewForm.getRating();
                String content = reviewForm.getComment();
                Review review = new Review(user,rating,content, LocalDate.now());
                wineryService.setNewReview(wineryId,review);

                return "redirect:/wineries/"+wineryId;
            } else {
                return "redirect:/error";
            }
        } else {
            return "LogIn";
        }
    }

}
