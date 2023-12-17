package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;

public interface WinerySorting {
    List<Winery> sortWineriesByStatus(List<Winery> wineries);
    List<Winery> sortWineriesByRating(List<Winery> wineries);
    List<Winery> sortWineriesByPopularity(List<Winery> wineries);
}
