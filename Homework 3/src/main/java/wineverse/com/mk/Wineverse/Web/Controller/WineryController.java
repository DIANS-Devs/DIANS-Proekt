package wineverse.com.mk.Wineverse.Web.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wineverse.com.mk.Wineverse.Config.LogIn.UserInfoUserDetails;
import wineverse.com.mk.Wineverse.Form.ReviewForm;
import wineverse.com.mk.Wineverse.Model.*;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;
import wineverse.com.mk.Wineverse.Service.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private final TypeService typeService;
    private final WinerySortingService winerySortingService;

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

    private void setDefaultSearchParameters(Model model, HttpSession session){
        SearchQuery searchQuery = new SearchQuery("", (float)0, (float)300, cityService.findCity("Цела Македонија"), wineryService.getAllWineries());
        setSearchAttributes(model, searchQuery);
        addSearchQueryAttribute(session, searchQuery);
    }

    private List<Winery> getFavorites(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (userService.getUserByUsername(auth.getName()).isPresent()){
            User user = userService.getUserByUsername(auth.getName()).get();
            return wineryService.getWineriesByIds(user.getFavorites());
        }
        return new ArrayList<>();
    }

    @GetMapping()
    public String getResultsMapping(Model model, HttpSession session,  @ModelAttribute("wineries") ArrayList<Winery> filtered_wineries) {
        setCitiesAttribute(model);
        //TODO filtered wineries on back click from post
        if(filtered_wineries != null && filtered_wineries.size() != 0){
            model.addAttribute("wineries", filtered_wineries);
        }
        else{
            model.addAttribute("wineries", wineryService.getAllWineries());
            setDefaultSearchParameters(model, session);
        }
        model.addAttribute("favorites", getFavorites());

        return "Wineries";
    }
    @PostMapping()
    public String postResultsMapping(@RequestParam(name="name", required = false) String wineryName,
                                        @RequestParam(name = "rating", required = false) Float wineryRating,
                                        @RequestParam(name = "distance", required = false) Float wineryDistance,
                                        @RequestParam(name = "location", required = false) String wineryCityName,
                                        @RequestParam(name = "sort", required = false) String sortingMethod,
                                        Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        //TODO SHOULD BE MODIFIED TO ID, NOT BY NAME
        setCitiesAttribute(model);
        SearchQuery retrievedQuery = (SearchQuery) session.getAttribute("searchQuery");

        String userLocation = (String) session.getAttribute("userGeolocation");

        if(retrievedQuery != null && wineryName == null && wineryCityName == null && wineryRating == null && wineryDistance == null){
            if(sortingMethod != null) {
                List<Winery> test = winerySortingService.sortWineries(sortingMethod, retrievedQuery, userLocation);
            }
            setSearchAttributes(model, retrievedQuery);
            redirectAttributes.addFlashAttribute("wineries", null);

            return "Wineries";
//            return "redirect:/wineries";
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
        List<Winery> filtered_wineries = wineryService.filteredWineries(wineryName, wineryRating, wineryDistance, wineryCity, userLocation);

        SearchQuery searchQuery = new SearchQuery(wineryName, wineryRating, wineryDistance, wineryCity, filtered_wineries);
        setSearchAttributes(model, searchQuery);
        addSearchQueryAttribute(session, searchQuery);
        redirectAttributes.addFlashAttribute("wineries", filtered_wineries);
        model.addAttribute("favorites", getFavorites());

        return "Wineries";
    }

    @GetMapping("/{id}")
    public String getWineryById(@PathVariable Long id, Model model) {
        //TODO ADD ERROR
        Winery winery = wineryService.getWineryById(id).get();
        setCitiesAttribute(model);
        model.addAttribute("winery", winery);
        model.addAttribute("favorites", getFavorites());

        List<Review> reviews = winery.getReviews();
        model.addAttribute("reviews", reviews);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //TODO fix this later
        model.addAttribute("userReview", "");

        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserInfoUserDetails userDetails) {

                User user = null;
                String userName = userDetails.getUsername();
                Optional<User> userOptional = userService.getUserByUsername(userName).stream().findFirst();

                if (userOptional.isPresent())
                    user = userOptional.get();

                try {
                    Review userReview = wineryService.getUserReviewForWinery(id, user);
                    model.addAttribute("userReview", userReview);

                } catch (Exception ignored){
                    model.addAttribute("userReview", "error");
                }

            } else {
                return "redirect:/error";
            }
        }
        return "WineryDetails";
    }

    @GetMapping("/map")
    public String showWineriesMap(Model model, HttpSession session){
        List<String> wineries = wineryService.getWineriesAsString();
        model.addAttribute("wineriesList", wineries);
        setCitiesAttribute(model);

        setDefaultSearchParameters(model ,session);

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

    @GetMapping("/add")
    public String showWineriesAdd(Model model){
        List<Type> types = typeService.findAll();
        List<City>cities = cityService.getAllCities().stream().skip(1).toList();
        model.addAttribute("cities", cities);
        model.addAttribute("types", types);
        return "WineriesAdd";
    }

    @PostMapping("/addedWinery")
    public String saveWinery(@RequestParam String name, @RequestParam("types") List<Long> typeIds,
                             @RequestParam String address, @RequestParam String city,
                             @RequestParam String phoneNumber,@RequestParam OperationalStatus operationalStatus, @RequestParam String internationalPhoneNumber,
                             @RequestParam String startTime,@RequestParam String endTime, @RequestParam String website,@RequestParam Boolean wheelchairAccess,
                             @RequestParam Float latitude, @RequestParam Float longitude){
        List<Type> types = typeIds.stream()
                .map(id -> typeService.findById(id).orElse(null))
                .collect(Collectors.toList());
        String workingTime = startTime + " - " + endTime;
        City city1 = cityService.findCity(city);
        Winery winery = new Winery(name, types, address, city1, phoneNumber, internationalPhoneNumber, workingTime, website, operationalStatus, wheelchairAccess, latitude, longitude);
        wineryService.saveWinery(winery);
        return "redirect:/wineries";
    }

        @PostMapping("/submitReview")
        public String submitReview(@ModelAttribute ReviewForm reviewForm, Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
                Object principal = authentication.getPrincipal();

                if (principal instanceof UserInfoUserDetails userDetails) {

                    User user = null;
                    String userName = userDetails.getUsername();
                    Optional<User> userOptional = userService.getUserByUsername(userName).stream().findFirst();

                    if (userOptional.isPresent())
                        user = userOptional.get();

                    Long wineryId = reviewForm.getWineryId();
                    Integer rating = reviewForm.getRating();
                    String content = reviewForm.getComment();

                    Review review;

                    try {
                        review = wineryService.getUserReviewForWinery(wineryId,user);
                        review.setRating(reviewForm.getRating());
                        review.setContent(reviewForm.getComment());
                    } catch (Exception e){
                        review = new Review(user,rating,content, LocalDate.now());
                    }
                    wineryService.setNewReview(wineryId,review);
                    model.addAttribute("reviews", wineryService.getWineryById(wineryId).get().getReviews());

                    return "redirect:/wineries/"+wineryId;
                } else {
                    return "redirect:/error";
                }
            }
            else {
                return "LogIn";
            }
        }

}
