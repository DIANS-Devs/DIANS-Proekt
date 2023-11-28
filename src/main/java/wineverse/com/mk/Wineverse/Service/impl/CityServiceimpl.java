package wineverse.com.mk.Wineverse.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Repository.CityRepository;
import wineverse.com.mk.Wineverse.Service.CityService;
import wineverse.com.mk.Wineverse.model.City;

import java.util.List;

@Service
public class CityServiceimpl implements CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityServiceimpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> all_Cities() {
        return cityRepository.getCities();
    }

    @Override
    public City find_city(String name) {
        return cityRepository.find_city(name);
    }
}
