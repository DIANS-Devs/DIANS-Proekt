package wineverse.com.mk.Wineverse.Data;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;
import wineverse.com.mk.Wineverse.Model.Type;
import wineverse.com.mk.Wineverse.Model.User;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Repository.CityRepository;
import wineverse.com.mk.Wineverse.Repository.TypeRepository;
import wineverse.com.mk.Wineverse.Repository.UserRepository;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DataHolder {
    private final CityRepository cityRepository;
    private final TypeRepository typeRepository;
    private final WineryRepository wineryRepository;
    private final UserRepository userRepository;

    //    public DataHolder(CityRepository cityRepository, TypeRepository typeRepository, WineryRepository wineryRepository, UserRepository userRepository) {
//        this.cityRepository = cityRepository;
//        this.typeRepository = typeRepository;
//        this.wineryRepository = wineryRepository;
//        this.userRepository = userRepository;
//    }

    @PostConstruct
    public void init() {
        List<String> all_cities = Arrays.asList(
                "Берово", "Битола", "Богданци", "Валандово", "Велес", "Виница", "Гевгелија",
                "Гостивар", "Дебар", "Делчево", "Демир Капија", "Демир Хисар", "Кавадарци",
                "Кичево", "Кочани", "Кратово", "Крива Паланка", "Крушево", "Куманово",
                "Македонски Брод", "Македонска Каменица", "Неготино", "Охрид", "Пехчево",
                "Прилеп", "Пробиштип", "Радовиш", "Ресен", "Свети Николе", "Скопје",
                "Струга", "Струмица", "Тетaово", "Штип");

        if(cityRepository.findAll().isEmpty()) {
            for (String city : all_cities) {
                cityRepository.save(new City(city));
            }
        }

        List<String> all_types = Arrays.asList("храна", "популарно место", "туристичка атракција", "вино", "бар", "установа");

        if (typeRepository.findAll().isEmpty()){
            for (String type: all_types){
                typeRepository.save(new Type(type));
            }
        }

        List<Winery> wineryList = new ArrayList<>();
        List<Type> types = new ArrayList<>();
        List<Type> types1 = new ArrayList<>();
        List<Type> types2 = new ArrayList<>();
        List<Type> types3 = new ArrayList<>();

        types.add(typeRepository.findFirstByType("храна"));
        types.add(typeRepository.findFirstByType("популарно место"));

        types1.add(typeRepository.findFirstByType("вино"));
        types1.add(typeRepository.findFirstByType("бар"));

        types2.add(typeRepository.findFirstByType("вино"));
        types2.add(typeRepository.findFirstByType("популарно место"));

        types3.add(typeRepository.findFirstByType("храна"));
        types3.add(typeRepository.findFirstByType("вино"));

        if(wineryRepository.findAll().isEmpty()){
            wineryRepository.save(new Winery("Борд живот", types,"Улица 103, Кучково, Општина Ѓорче Петров, Град Скопје, Скопски СР", cityRepository.findByName("Скопје"),"070 331 114","+389 70 331 114","Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float)42.0586418, (float)21.3176565));
            wineryRepository.save(new Winery("Винарите Камник", types1,"Камник, Товарник 1, Ченто, Гази Баба, Скопје, Општина Гази Баба, Град Скопје, Скопски СР, 1001", cityRepository.findByName("Скопје"),"070 333 666","+389 70 331 114","Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float)42.0077514, (float)21.4902808));
            wineryRepository.save(new Winery("Винарија со слики", types1,"67, Македонска Преродба, Оризари, Ѓорче Петров, Скопје, Општина Ѓорче Петров, Град Скопје, Скопски СР, 1060", cityRepository.findByName("Свети Николе"),"071 331 114","+389 70 331 114","Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float)42.0037876, (float)21.9278854));
            wineryRepository.save(new Winery("Винарија Картал", types2,"Улица 103, Кучково, Општина Ѓорче Петров, Град Скопје, Скопски СР", cityRepository.findByName("Велес"),"070 331 114","+389 74 331 114","Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float)41.7988315, (float)21.7721987));

//            wineryRepository.saveAll(wineryList);
        }


        User user = new User("admin", "admin", "admin", "admin", "074444244");
        if(userRepository.findAll().isEmpty())
            userRepository.save(user);
    }
}
