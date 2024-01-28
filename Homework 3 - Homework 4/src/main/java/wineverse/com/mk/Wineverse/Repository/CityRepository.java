package wineverse.com.mk.Wineverse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.Model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findByName(String name);
}
