package wineverse.com.mk.Wineverse.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.model.Winery;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WineryRepository {
    private final CityRepository cityRepository;
    private final List<Winery>  wineryRepository = new ArrayList<>();

    @Autowired
    public WineryRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        initializeWineries();
    }
    private void initializeWineries() {
        Winery w1 = new Winery("Popova Kula","Wine Boulevard No.1 Demir Kapija","Понеделник-Петок 10:00-22:00", cityRepository.find_city("Демир Капија"),"http://www.popovakula.com.mk/", "gglmapsid",	"076 432 630",	"+389 76 432 630", (float) 41.22, (float) 22.2);
        cityRepository.find_city("Демир Капија").add_winery_in_city(w1);
        Winery w2 = new Winery("Wine Tours Macedonia",		"8-mi Mart 18, Skopje", 	"Понеделник-Петок 10:00-22:00",cityRepository.find_city("Скопје"),"https://www.winetours.mk/","gglsmaps21w", "070 958 307",	"+389 70 958 307", (float) 42.009, (float) 21.424);
        cityRepository.find_city("Скопје").add_winery_in_city(w2);
        Winery w3 = new Winery("Popova Sapka","Wine Boulevard No.1 Demir Kapija","Понеделник-Петок 10:00-22:00", cityRepository.find_city("Демир Капија"),"http://www.popovakula.com.mk/", "gglmapsid",	"076 432 630",	"+389 76 432 630", (float) 41.22, (float) 22.2);
        cityRepository.find_city("Демир Капија").add_winery_in_city(w3);

        wineryRepository.add(w1);
        wineryRepository.add(w2);
        wineryRepository.add(w3);
    }

    public List<Winery> get_all_wineries() {
        return wineryRepository;
    }
}
