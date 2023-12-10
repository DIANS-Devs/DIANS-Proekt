package mk.com.wineverse.Repository;

import org.springframework.stereotype.Repository;
import mk.com.wineverse.Data.InMemoryDataHolder;
import mk.com.wineverse.Model.City;

import java.util.List;

@Repository
public class CityRepository {
//    List<String> all_cities = Arrays.asList(
//            "Берово", "Битола", "Богданци", "Валандово", "Велес", "Виница", "Гевгелија",
//            "Гостивар", "Дебар", "Делчево", "Демир Капија", "Демир Хисар", "Кавадарци",
//            "Кичево", "Кочани", "Кратово", "Крива Паланка", "Крушево", "Куманово",
//            "Македонски Брод", "Македонска Каменица", "Неготино", "Охрид", "Пехчево",
//            "Прилеп", "Пробиштип", "Радовиш", "Ресен", "Свети Николе", "Скопје",
//            "Струга", "Струмица", "Тетaово", "Штип");
//    @Getter
//    List<City> cities = new ArrayList<>();
//
//    public CityRepository() {
//        for(String city : all_cities){
//            cities.add(new City(city));
//        }
//    }

    public City findCity(String name) {
       return InMemoryDataHolder.cities.stream()
               .filter(city->city.getName().equals(name))
               .findFirst().orElse(null);
    }

    public List<City> getAllCities(){
        return InMemoryDataHolder.cities;
    }
}
