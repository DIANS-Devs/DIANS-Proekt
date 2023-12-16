package wineverse.com.mk.Wineverse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;

@Repository
public interface WineryRepository extends JpaRepository<Winery, Long> {
    //Worst case: Name="", rating = 0, distance = 300, City = Цела Македонија
    List<Winery> findByCity(City city);
    List<Winery> findByNameContaining(String name);
    List<Winery> findByRatingGreaterThanEqual(Float rating);
}
