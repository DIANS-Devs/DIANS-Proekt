package wineverse.com.mk.Wineverse.Data;

import jakarta.annotation.PostConstruct;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Repository.CityRepository;
import org.springframework.stereotype.Component;
import wineverse.com.mk.Wineverse.Model.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InMemoryDataHolder {
    public static List<City> cities = new ArrayList<>();
    public static List<Winery>  wineryList = new ArrayList<>();

    private final CityRepository cityRepository;

    public InMemoryDataHolder(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    @PostConstruct
    public void init() {
        List<String> all_cities = Arrays.asList(
                "Берово", "Битола", "Богданци", "Валандово", "Велес", "Виница", "Гевгелија",
                "Гостивар", "Дебар", "Делчево", "Демир Капија", "Демир Хисар", "Кавадарци",
                "Кичево", "Кочани", "Кратово", "Крива Паланка", "Крушево", "Куманово",
                "Македонски Брод", "Македонска Каменица", "Неготино", "Охрид", "Пехчево",
                "Прилеп", "Пробиштип", "Радовиш", "Ресен", "Свети Николе", "Скопје",
                "Струга", "Струмица", "Тетaово", "Штип");

        for(String city : all_cities){
            cities.add(new City(city));
        }


        Winery w1 = new Winery("Popova Kula","Wine Boulevard No.1 Demir Kapija","Понеделник-Петок 10:00-22:00", cityRepository.findCity("Демир Капија"),"http://www.popovakula.com.mk/", "gglmapsid",	"076 432 630",	"+389 76 432 630", (float) 41.1, (float) 21.2);
        cityRepository.findCity("Демир Капија").addWineryInCity(w1);
        Winery w2 = new Winery("Wine Tours Macedonia","8-mi Mart 18, Skopje","Понеделник-Петок 10:00-22:00",cityRepository.findCity("Скопје"),"https://www.winetours.mk/","gglsmaps21w", "070 958 307",	"+389 70 958 307", (float) 42.009, (float) 21.424);
        cityRepository.findCity("Скопје").addWineryInCity(w2);
        Winery w3 = new Winery("Popova Sapka","Wine Boulevard No.1 Demir Kapija","Понеделник-Петок 10:00-22:00", cityRepository.findCity("Демир Капија"),"http://www.popovakula.com.mk/", "gglmapsid",	"076 432 630",	"+389 76 432 630", (float) 41.22, (float) 22.2);
        cityRepository.findCity("Демир Капија").addWineryInCity(w3);

        wineryList.add(w1);
        wineryList.add(w2);
        wineryList.add(w3);


    }
}
