package wineverse.com.mk.Wineverse.Data;

import jakarta.annotation.PostConstruct;
import org.hibernate.type.TrueFalseConverter;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;
import wineverse.com.mk.Wineverse.Model.Type;
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

        List<Type> types = List.of(new Type("hrana"), new Type("vino"), new Type("popularno"));
        Winery w1 = new Winery("Popova Kula", types, "Wine Boulevard No.1 Demir Kapija",cityRepository.findCity("Велес"), "077-240-240", "+389 77-240-240", "Понеделник-Петок 10:00-22:00","http://www.popovakula.com.mk/", OperationalStatus.OPEN, true, (float) 41.1, (float) 21.2);
//        cityRepository.findCity("Демир Капија").addWineryInCity(w1);
        Winery w2 = new Winery("Chatau winery", types, "Wine Boulevard No.1 Demir Kapija",cityRepository.findCity("Скопје"), "077-240-240", "+389 77-240-240", "Понеделник-Петок 10:00-22:00","http://www.popovakula.com.mk/", OperationalStatus.OPEN, true, (float) 41.1, (float) 21.2);
//        cityRepository.findCity("Велес").addWineryInCity(w2);
        Winery w3 = new Winery("Popova Sapka", types, "Wine Boulevard No.1 Demir Kapija",cityRepository.findCity("Дебар"), "077-240-240", "+389 77-240-240", "Понеделник-Петок 10:00-22:00","http://www.popovakula.com.mk/", OperationalStatus.OPEN, true, (float) 41.1, (float) 21.2);
//        cityRepository.findCity("Скопје").addWineryInCity(w3);
        Winery w4 = new Winery("Beertija", types, "Wine Boulevard No.1 Demir Kapija",cityRepository.findCity("Демир Капија"), "077-240-240", "+389 77-240-240", "Понеделник-Петок 10:00-22:00","http://www.popovakula.com.mk/", OperationalStatus.OPEN, true, (float) 41.1, (float) 21.2);
//        cityRepository.findCity("Скопје").addWineryInCity(w3);

        wineryList.add(w1);
        wineryList.add(w2);
        wineryList.add(w3);
        wineryList.add(w4);
    }
}
