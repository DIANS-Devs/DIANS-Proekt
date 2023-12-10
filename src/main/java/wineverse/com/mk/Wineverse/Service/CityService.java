package mk.com.wineverse.Service;

import mk.com.wineverse.Model.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities();
    City findCity(String name);
}
