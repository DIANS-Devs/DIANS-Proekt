package wineverse.com.mk.Wineverse.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.model.City;
import wineverse.com.mk.Wineverse.model.Winery;

import java.util.ArrayList;
import java.util.List;

@Service
public class WineryServiceimpl implements WineryService {
    private WineryRepository wineryRepository;

    @Autowired
    public WineryServiceimpl(WineryRepository wineryRepository) {
        this.wineryRepository = wineryRepository;
    }

    @Override
    public List<Winery> all_wineries() {
        return wineryRepository.get_all_wineries();
    }

    @Override
    public List<Winery> filtered_wineries(String name, int rating, float distance, City city) {
        if(!name.equals("") && city!=null && (!city.getName().equals("Цела Македонија"))){
            List<Winery> wineries_by_name =  find_winery_byname(name);
            List<Winery> wineries_by_city = find_wineries_bycity(city);
            List<Winery> matching_wineries = new ArrayList<>();
            for(Winery w: wineries_by_name){
                for(Winery w1: wineries_by_city){
                    if(w == w1)
                        matching_wineries.add(w);
                }
            }
            return matching_wineries;

        } else if (!name.equals("")) {
            return find_winery_byname(name);
        } else if (city != null && !city.getName().equals("Цела Македонија")) {
            return find_wineries_bycity(city);
        }

//        if(city != null) {
//            return find_wineries_bycity(city);
//        }
        return wineryRepository.get_all_wineries();
    }

    @Override
    public List<Winery> find_winery_byname(String name) {
        name = name.toLowerCase();
        List<Winery> wineries = wineryRepository.get_all_wineries();
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
    public List<Winery> find_wineries_bycity(City city) {
        return city.getCity_wineries();
    }

    @Override
    public Winery getWineryById(Long id) {
        return wineryRepository.get_all_wineries().stream().filter(w -> w.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Winery> getWineriesByIds(List<String> favoriteWineryIds) {
        List<Winery> return_wineries = new ArrayList<>();
        for(Winery w: wineryRepository.get_all_wineries()){
            for(String ids : favoriteWineryIds){
                Long id = Long.parseLong(ids);
                if(id == w.getId())
                    return_wineries.add(w);
            }
        }
        return return_wineries;
    }
}
