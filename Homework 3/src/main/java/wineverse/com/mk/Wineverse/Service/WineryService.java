package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;

public interface WineryService {
    List<Winery> getAllWineries();
    List<Winery> filteredWineries(String name, int rating, float distance, City city);
    List<Winery> findWineriesByName(String name);
    List<Winery> findWineriesByCity(City city);
    Winery getWineryById(Long id);
    List<Winery> getWineriesByIds(List<String> favoriteWineryIds);
    List<String> getWineriesAsString();
    List<String> getFavouriteWineriesAsString();
}
