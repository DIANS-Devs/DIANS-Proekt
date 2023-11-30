package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.model.City;

import java.util.List;

public interface CityService {
    List<City> all_Cities();
    City find_city(String name);
}
