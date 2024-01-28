package wineverse.com.mk.Wineverse.Service.impl;

import lombok.AllArgsConstructor;
import wineverse.com.mk.Wineverse.Repository.CityRepository;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.Model.City;

import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City findCity(String name) {
        return cityRepository.findByName(name);
    }
}
