package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;
import java.util.Optional;

public interface WineryCacheService {
    List<Winery> listAll();
    void update();
    Optional<Winery> findById(Long id);
    List<Winery> findAllById(List<Long> id);
    List<Winery> findByNameContaining(String name);
    List<Winery> findByRatingGreaterThanEqual(Float rating);
    List<Winery> findByCity(City city);
}
