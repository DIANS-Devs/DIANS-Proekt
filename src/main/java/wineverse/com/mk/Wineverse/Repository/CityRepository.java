package wineverse.com.mk.Wineverse.Repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.model.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CityRepository {
    List<String> all_cities = Arrays.asList(
            "Берово", "Битола", "Богданци", "Валандово", "Велес", "Виница", "Гевгелија",
            "Гостивар", "Дебар", "Делчево", "Демир Капија", "Демир Хисар", "Кавадарци",
            "Кичево", "Кочани", "Кратово", "Крива Паланка", "Крушево", "Куманово",
            "Македонски Брод", "Македонска Каменица", "Неготино", "Охрид", "Пехчево",
            "Прилеп", "Пробиштип", "Радовиш", "Ресен", "Свети Николе", "Скопје",
            "Струга", "Струмица", "Тетaово", "Штип");
    @Getter
    List<City> cities = new ArrayList<>();

    public CityRepository() {
        for(String city : all_cities){
            cities.add(new City(city));
        }
    }

    public City find_city(String name)
    {
       return cities.stream().filter(city->city.getName().equals(name)).findFirst().orElse(null);
    }
}
