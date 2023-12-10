package mk.com.wineverse.Service.impl;

import lombok.AllArgsConstructor;
import mk.com.wineverse.Repository.WineryRepository;
import org.springframework.stereotype.Service;
import mk.com.wineverse.Service.WineryService;
import mk.com.wineverse.Model.City;
import mk.com.wineverse.Model.Winery;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WineryServiceImpl implements WineryService {
    private final WineryRepository wineryRepository;

    @Override
    public List<Winery> getAllWineries() {
        return wineryRepository.getAllWineries();
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
        return wineryRepository.getAllWineries();
    }

    @Override
    public List<Winery> findWineriesByName(String name) {
        name = name.toLowerCase();
        List<Winery> wineries = wineryRepository.getAllWineries();
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
        return city.getCityWineries();
    }

    @Override
    public Winery getWineryById(Long id) {
        return wineryRepository.getAllWineries().stream().filter(w -> w.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Winery> getWineriesByIds(List<String> favoriteWineryIds) {
        List<Winery> return_wineries = new ArrayList<>();
        for(Winery w: wineryRepository.getAllWineries()){
            for(String ids : favoriteWineryIds){
                long id = Long.parseLong(ids);
                if(id == w.getId())
                    return_wineries.add(w);
            }
        }
        return return_wineries;
    }
}
