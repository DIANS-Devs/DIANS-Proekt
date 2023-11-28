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
        if(!name.equals("")){
            List<Winery> return_wineries = new ArrayList<Winery>();
            List<Winery> wineries = find_winery_byname(name);
            return return_wineries;
        }
        return wineryRepository.get_all_wineries();

        //TODO
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

        //TODO
    }
}
