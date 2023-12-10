package wineverse.com.mk.Wineverse.Repository;

import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.Data.InMemoryDataHolder;
import wineverse.com.mk.Wineverse.Model.City;

import java.util.List;

@Repository
public class CityRepository {
    public City findCity(String name) {
       return InMemoryDataHolder.cities.stream()
               .filter(city->city.getName().equals(name))
               .findFirst().orElse(null);
    }

    public List<City> getAllCities(){
        return InMemoryDataHolder.cities;
    }
}
