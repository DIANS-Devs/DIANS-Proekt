package wineverse.com.mk.Wineverse.Service;

import wineverse.com.mk.Wineverse.model.City;

import java.util.List;

public interface CityService {
    public List<City> all_Cities();
    public City find_city(String name);
}
