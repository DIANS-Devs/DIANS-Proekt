package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Model.User;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;
import java.util.Optional;

public interface WineryService {
    List<Winery> getAllWineries();
    List<Winery> filteredWineries(String name, Float rating, Float distance, City city, String userLocation);
    Optional<Winery> getWineryById(Long id);
    List<Winery> getWineriesByIds(List<Long> favoriteWineryIds);
    List<String> getWineriesAsString();
    List<String> getFavoritesAsString(List<Long> ids);
    void setNewReview(Long wineryId, Review review);
    void saveWinery(Winery winery);

    Review getUserReviewForWinery(Long wineryId, User user);
}
