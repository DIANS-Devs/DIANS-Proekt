package wineverse.com.mk.Wineverse.Service.impl;

import lombok.AllArgsConstructor;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WineryServiceImpl implements WineryService {
    private final WineryRepository wineryRepository;

    @Override
    public List<Winery> getAllWineries() {
        return wineryRepository.findAll();
    }

    @Override
    public List<Winery> filteredWineries(String name, int rating, float distance, City city) {
        if(!name.isEmpty() && city!=null && (!city.getName().equals("Цела Македонија"))){
            List<Winery> wineries_by_name =  findWineriesByName(name);
            List<Winery> wineries_by_city = findWineriesByCity(city);
            List<Winery> matching_wineries = new ArrayList<>();
            for(Winery w: wineries_by_name){
                for(Winery w1: wineries_by_city){
                    if(w == w1)
                        matching_wineries.add(w);
                }
            }
            return matching_wineries;

        } else if (!name.isEmpty()) {
            return findWineriesByName(name);
        } else if (city != null && !city.getName().equals("Цела Македонија")) {
            return findWineriesByCity(city);
        }

//        if(city != null) {
//            return find_wineries_bycity(city);
//        }
        return wineryRepository.findAll();
    }

    @Override
    public List<Winery> findWineriesByName(String name) {
        name = name.toLowerCase();
        List<Winery> wineries = wineryRepository.findAll();
        List<Winery> matchingWineries = new ArrayList<>();
        for (Winery w : wineries) {
            String wineryName = w.getName().toLowerCase();
            if (wineryName.contains(name)) {
                matchingWineries.add(w);
            }
        }
        return matchingWineries;
    }

    @Override
    public List<Winery> findWineriesByCity(City city) {
        return city.getWineriesInCity();
    }

    @Override
    public Winery getWineryById(Long id) {
        return wineryRepository.findAll().stream().filter(w -> w.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Winery> getWineriesByIds(List<String> favoriteWineryIds) {
        List<Winery> return_wineries = new ArrayList<>();
        for(Winery w: wineryRepository.findAll()){
            for(String ids : favoriteWineryIds){
                long id = Long.parseLong(ids);
                if(id == w.getId())
                    return_wineries.add(w);
            }
        }
        return return_wineries;
    }

    @Override
    public List<String> getWineriesAsString(){
        List<Winery> wineries = wineryRepository.findAll();
        return wineries.stream()
                .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFavouriteWineriesAsString() {
        //TODO NOT THIS CODE, CHANGE IT
        List<Winery> wineries = wineryRepository.findAll();
        return wineries.stream()
                .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                .collect(Collectors.toList());
    }
}
