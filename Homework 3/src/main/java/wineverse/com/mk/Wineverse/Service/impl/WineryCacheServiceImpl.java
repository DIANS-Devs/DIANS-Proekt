package wineverse.com.mk.Wineverse.Service.impl;

import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Exceptions.InvalidWineryIdException;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;
import wineverse.com.mk.Wineverse.Service.WineryCacheService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WineryCacheServiceImpl implements WineryCacheService {
    private final WineryRepository wineryRepository;
    private List<Winery> cachedWineries;

    public WineryCacheServiceImpl(WineryRepository wineryRepository) {
        this.wineryRepository = wineryRepository;
        cachedWineries = new ArrayList<>();
        updateCache();
    }

    private void updateCache() {
        if (cachedWineries.isEmpty()) {
            cachedWineries = wineryRepository.findAll();
        }
    }

    @Override
    public List<Winery> listAll() {
        updateCache();
        return cachedWineries;
    }

    @Override
    public void update() {
        cachedWineries = wineryRepository.findAll();
    }

    @Override
    public Optional<Winery> findById(Long id) {
        updateCache();
        return cachedWineries.stream()
                .filter(winery -> winery.getId().equals(id)).findFirst();
    }

    @Override
    public List<Winery> findAllById(List<Long> ids){
        updateCache();
        return cachedWineries.stream()
                .filter(winery -> ids.contains(winery.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Winery> findByNameContaining(String name) {
        updateCache();
        return cachedWineries.stream()
                .filter(winery -> winery.getName().contains(name)).collect(Collectors.toList());
    }

    @Override
    public List<Winery> findByCity(City city) {
        updateCache();
        return cachedWineries.stream()
                .filter(winery -> winery.getCity().getId().equals(city.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Winery> findByRatingGreaterThanEqual(Float rating) {
        updateCache();
        return cachedWineries.stream()
                .filter(winery -> winery.getRating() >= rating).collect(Collectors.toList());
    }
}
