package mk.com.wineverse.Service.impl;

import lombok.AllArgsConstructor;
import mk.com.wineverse.Repository.CityRepository;
import org.springframework.stereotype.Service;
import mk.com.wineverse.Service.CityService;
import mk.com.wineverse.Model.City;

import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceimpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.getAllCities();
    }

    @Override
    public City findCity(String name) {
        return cityRepository.findCity(name);
    }
}
