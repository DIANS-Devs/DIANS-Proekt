package wineverse.com.mk.Wineverse.Service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.HelpServices.DistanceCalculator;
import wineverse.com.mk.Wineverse.Model.*;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;
import wineverse.com.mk.Wineverse.Repository.CityRepository;
import wineverse.com.mk.Wineverse.Repository.ReviewRepository;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;
import wineverse.com.mk.Wineverse.Service.WineryCacheService;
import wineverse.com.mk.Wineverse.Service.WineryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class WineryServiceImpl implements WineryService {
    private final WineryRepository wineryRepository;
    private final ReviewRepository reviewRepository;
    private final WineryCacheService wineryCacheService;
    private final CityRepository cityRepository;
    private final DistanceCalculator distanceCalculator;

    public WineryServiceImpl(WineryRepository wineryRepository, ReviewRepository reviewRepository, CityRepository cityRepository, DistanceCalculator distanceCalculator) {
        this.wineryRepository = wineryRepository;
        this.reviewRepository = reviewRepository;
        this.cityRepository = cityRepository;
        this.wineryCacheService = WineryCacheServiceImpl.getInstance(wineryRepository);
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public Review getUserReviewForWinery(Long wineryId, User user) {
        Optional<Review> userReview = wineryCacheService.findById(wineryId).get().getReviews()
                .stream()
                .filter(review -> review.getAuthor().equals(user))
                .findFirst();

        if (userReview.isPresent()) {
            return userReview.get();
        } else {
            // Handle the case when the review is not present, e.g., throw an exception or return a default value
            throw new EntityNotFoundException("Review not found for Winery ID: " + wineryId + " and User ID: " + user.getId());
        }
    }

    @Override
    public List<Winery> getAllWineries() {
        return wineryCacheService.listAll();
    }

    @Override
    @Transactional
    public void setNewReview(Long wineryId, Review review) {
        Winery winery = wineryCacheService.findById(wineryId).orElseThrow();

        // Check if the review already exists
        Optional<Review> existingReview = winery.getReviews().stream()
                .filter(item -> item.getAuthor().equals(review.getAuthor()))
                .findFirst();

        if (existingReview.isPresent()) {
            // If the review exists, update its properties
            Review reviewToUpdate = existingReview.get();
            reviewToUpdate.setRating(review.getRating());
            reviewToUpdate.setContent(review.getContent());
            winery.addReview(reviewToUpdate);
            reviewRepository.save(reviewToUpdate);
        } else {
            // If the review doesn't exist, add it to the winery's reviews
            winery.addReview(review);
            reviewRepository.save(review);
        }
        wineryRepository.save(winery);
        //Need to update the cache after each review,
        // or we can update just the element with the new review,
        // but that would be the same since we need to search for element O(n) and then update it
        wineryCacheService.update();
    }

    @Override
    //Worst case: Name="", rating = 0, distance = 300, City = Цела Македонија
    public List<Winery> filteredWineries(String name, Float rating, Float distance, City city, String userLocation) {
        List<Winery> name_wineries = wineryCacheService.findByNameContaining(name);
        List<Winery> rating_wineries = wineryCacheService.findByRatingGreaterThanEqual(rating);
        List<Winery> city_wineries;
        if (!city.getName().equals("Цела Македонија")) {
            city_wineries = wineryCacheService.findByCity(city);
        } else {
            city_wineries = wineryCacheService.listAll();
        }
        List<Winery> distance_wineries = wineryCacheService
                .listAll()
                .stream()
                .filter(winery -> distance >= distanceCalculator.getDistance(userLocation, winery.getLatitude(), winery.getLongitude()))
                .toList();
        
        List<Winery> interceptWineries = new ArrayList<>(name_wineries);
        interceptWineries.retainAll(rating_wineries);
        interceptWineries.retainAll(city_wineries);
        interceptWineries.retainAll(distance_wineries);
        return interceptWineries;
    }

    @Override
    public Optional<Winery> getWineryById(Long id) {
        return wineryCacheService.findById(id);
    }

    @Override
    public List<Winery> getWineriesByIds(List<Long> favoriteWineryIds) {
        return wineryCacheService.findAllById(favoriteWineryIds);
    }

    @Override
    public List<String> getWineriesAsString(){
        List<Winery> wineries = wineryCacheService.listAll();
        return wineries.stream()
                .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFavoritesAsString(List<Long> ids){
        List<Winery> wineries = wineryCacheService.findAllById(ids);
        return wineries.stream()
                .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveWinery(String name, List<Type> types, String address, String city, String phoneNumber, String internationalPhoneNumber, String workingTime, String website, OperationalStatus operationalStatus, Boolean wheelchairAccess, Float latitude, Float longitude) {
        City c = cityRepository.findByName(city);
        Winery winery = new Winery(name, types, address, c, phoneNumber, internationalPhoneNumber, workingTime, website, operationalStatus, wheelchairAccess, latitude, longitude);
        wineryRepository.save(winery);
        wineryCacheService.update();
    }
}
