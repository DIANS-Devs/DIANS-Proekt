package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.model.City;
import wineverse.com.mk.Wineverse.model.Winery;

import java.util.List;

public interface WineryService {
    List<Winery> all_wineries();
    List<Winery> filtered_wineries(String name, int rating, float distance, City city);
    List<Winery> find_winery_byname(String name);
    List<Winery> find_wineries_bycity(City city);
    Winery getWineryById(Long id);

    List<Winery> getWineriesByIds(List<String> favoriteWineryIds);
}
