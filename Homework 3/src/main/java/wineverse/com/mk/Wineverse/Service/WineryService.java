package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.*;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;

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
    void saveWinery(String name, List<Type> types, String address, String city, String phoneNumber, String internationalPhoneNumber, String workingTime, String website, OperationalStatus operationalStatus, Boolean wheelchairAccess, Float latitude, Float longitude);

    Review getUserReviewForWinery(Long wineryId, User user);
}
