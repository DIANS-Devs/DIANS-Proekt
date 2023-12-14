package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.Model.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities();
    City findCity(String name);
}
