package wineverse.com.mk.Wineverse.Service.impl;

import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;
import wineverse.com.mk.Wineverse.Service.WineryCacheService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WineryCacheServiceImpl implements WineryCacheService {
    private static WineryCacheServiceImpl instance;
    private final WineryRepository wineryRepository;
    private List<Winery> cachedWineries;

    private WineryCacheServiceImpl(WineryRepository wineryRepository) {
        this.wineryRepository = wineryRepository;
        cachedWineries = wineryRepository.findAll();
    }

    public static synchronized WineryCacheServiceImpl getInstance(WineryRepository wineryRepository) {
        if (instance == null) {
            instance = new WineryCacheServiceImpl(wineryRepository);
        }
        return instance;
    }

    @Override
    public List<Winery> listAll() {
        return cachedWineries;
    }

    @Override
    public void update() {
        cachedWineries = wineryRepository.findAll();
    }

    @Override
    public Optional<Winery> findById(Long id) {
        return cachedWineries.stream()
                .filter(winery -> winery.getId().equals(id)).findFirst();
    }

    @Override
    public List<Winery> findAllById(List<Long> ids){
        return cachedWineries.stream()
                .filter(winery -> ids.contains(winery.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Winery> findByNameContaining(String name) {
        return cachedWineries.stream()
                .filter(winery -> winery.getName().contains(name)).collect(Collectors.toList());
    }

    @Override
    public List<Winery> findByCity(City city) {
        return cachedWineries.stream()
                .filter(winery -> winery.getCity().getId().equals(city.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Winery> findByRatingGreaterThanEqual(Float rating) {
        return cachedWineries.stream()
                .filter(winery -> winery.getRating() >= rating).collect(Collectors.toList());
    }
}
