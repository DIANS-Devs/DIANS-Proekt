package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.SearchQuery;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;

public interface WinerySortingService {
    List<Winery> sortWineries(String sortingMethod, SearchQuery retrievedQuery, String userLocation);
}
