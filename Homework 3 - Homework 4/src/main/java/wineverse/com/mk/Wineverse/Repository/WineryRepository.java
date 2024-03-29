package wineverse.com.mk.Wineverse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;

@Repository
public interface WineryRepository extends JpaRepository<Winery, Long> {
    List<Winery> findByCity(City city);
    List<Winery> findByNameContaining(String name);
    List<Winery> findByRatingGreaterThanEqual(Float rating);
}
