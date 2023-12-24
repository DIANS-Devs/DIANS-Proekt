package wineverse.com.mk.Wineverse.Service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Model.User;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Repository.ReviewRepository;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;
import wineverse.com.mk.Wineverse.Service.WineryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WineryServiceImpl implements WineryService {
    private final WineryRepository wineryRepository;
    private final ReviewRepository reviewRepository;

    public static double getDistance(String userLocation, double wineryLat, double wineryLon){
        if (userLocation == null) return 0;

        double userLat = Double.parseDouble(userLocation.split(",")[0]);
        double userLon = Double.parseDouble(userLocation.split(",")[1]);

        double R = 6371; // Radius of the Earth in kilometers
        double dLat = Math.toRadians(wineryLat - userLat);
        double dLon = Math.toRadians(wineryLon - userLon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(wineryLat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        System.out.println("The distance is "+ R*c);
        return R * c; // Distance in kilometers
    }

    @Override
    public Review getUserReviewForWinery(Long wineryId, User user) {
        Optional<Review> userReview = wineryRepository.findById(wineryId).get().getReviews()
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
        return wineryRepository.findAll();
    }

    @Override
    public void setNewReview(Long wineryId, Review review) {
        Winery winery = wineryRepository.findById(wineryId).orElseThrow();

        // Check if the review already exists
        boolean reviewExists = winery.getReviews().stream()
                .anyMatch(item -> item.getAuthor().equals(review.getAuthor()));

        if (reviewExists) {
            // If the review exists, update its properties
            winery.getReviews().stream()
                    .filter(item -> item.getAuthor().equals(review.getAuthor()))
                    .findFirst()
                    .ifPresent(existingReview -> {
                        existingReview.setRating(review.getRating());
                        existingReview.setContent(review.getContent());
                        reviewRepository.save(existingReview);
                    });
        } else {
            // If the review doesn't exist, add it to the winery's reviews
            winery.addReview(review);
            reviewRepository.save(review);
        }
    }

    @Override
    //Worst case: Name="", rating = 0, distance = 300, City = Цела Македонија
    public List<Winery> filteredWineries(String name, Float rating, Float distance, City city, String userLocation) {
        //TODO implement distance filter
        List<Winery> name_wineries = wineryRepository.findByNameContaining(name);
        List<Winery> rating_wineries = wineryRepository.findByRatingGreaterThanEqual(rating);
        List<Winery> city_wineries;
        if (!city.getName().equals("Цела Македонија")) {
            city_wineries = wineryRepository.findByCity(city);
        } else {
            city_wineries = wineryRepository.findAll();
        }
        List<Winery> distance_wineries = wineryRepository
                .findAll()
                .stream()
                .filter(winery -> distance >= getDistance(userLocation, winery.getLatitude(), winery.getLongitude()))
                .toList();
        
        List<Winery> interceptWineries = new ArrayList<>(name_wineries);
        interceptWineries.retainAll(rating_wineries);
        interceptWineries.retainAll(city_wineries);
        interceptWineries.retainAll(distance_wineries);
        return interceptWineries;
    }

    @Override
    public Optional<Winery> getWineryById(Long id) {
        return wineryRepository.findById(id);
    }

    @Override
    public List<Winery> getWineriesByIds(List<Long> favoriteWineryIds) {
        return wineryRepository.findAllById(favoriteWineryIds);
    }

    @Override
    public List<String> getWineriesAsString(){
        List<Winery> wineries = wineryRepository.findAll();
        return wineries.stream()
                .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFavoritesAsString(List<Long> ids){
        List<Winery> wineries = wineryRepository.findAllById(ids);
        return wineries.stream()
                .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveWinery(Winery winery) {
        wineryRepository.save(winery);
    }
}
