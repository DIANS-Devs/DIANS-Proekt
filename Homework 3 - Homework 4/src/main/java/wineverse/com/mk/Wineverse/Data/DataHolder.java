package wineverse.com.mk.Wineverse.Data;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;
import wineverse.com.mk.Wineverse.Model.Type;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Repository.CityRepository;
import wineverse.com.mk.Wineverse.Repository.TypeRepository;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@AllArgsConstructor
public class DataHolder {
    private final CityRepository cityRepository;
    private final TypeRepository typeRepository;
    private final WineryRepository wineryRepository;

    @PostConstruct
    @Transactional
    public void init() {
        List<String> all_cities = Arrays.asList(
                "Цела Македонија", "Берово", "Битола", "Богданци", "Валандово", "Велес", "Виница", "Гевгелија",
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

        types.add(typeRepository.findFirstByType("храна"));
        types.add(typeRepository.findFirstByType("популарно место"));

        types1.add(typeRepository.findFirstByType("вино"));
        types1.add(typeRepository.findFirstByType("бар"));

        types2.add(typeRepository.findFirstByType("вино"));
        types2.add(typeRepository.findFirstByType("популарно место"));

        if(wineryRepository.findAll().isEmpty()){
            Winery winery1 = new Winery("Борд живот", types, "Улица 103, Кучково, Општина Ѓорче Петров", cityRepository.findByName("Скопје"), "070 331 114", "+389 70 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 42.0586418, (float) 21.3176565);

            Winery winery2 = new Winery("Винарите Камник", types1, "Камник, Товарник 1, Ченто, Гази Баба, Скопје, Општина Гази Баба", cityRepository.findByName("Скопје"), "070 333 666", "+389 70 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 42.0077514, (float) 21.4902808);

            Winery winery3 = new Winery("Винарија со слики", types1, "67, Македонска Преродба, Оризари, Ѓорче Петров", cityRepository.findByName("Скопје"), "071 331 114", "+389 70 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 42.0094835, (float) 21.351241);

            Winery winery4 = new Winery("Зик лозја", types2, "R2247, Павлешенци, Општина Свети Николе", cityRepository.findByName("Свети Николе"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 42.0037876, (float) 21.9278854);
            Winery winery5 = new Winery("Винарија „Шато Сопот“", types1, "L10104, Новачани", cityRepository.findByName("Велес"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.7988315, (float) 21.7721987);
            Winery winery6 = new Winery("Винарија на Валанд", types2, "1 Мај, Валандово", cityRepository.findByName("Валандово"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.3195595, (float) 22.5663368);
            Winery winery7 = new Winery("Винарија Јостела", types2, "Гевгелија", cityRepository.findByName("Гевгелија"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.1355959, (float) 22.4833692);
            Winery winery8 = new Winery("Винарија и дестилерија Захарчев", types1, "Семејна винарија „Захарчев“, 16, Косовска", cityRepository.findByName("Кавадарци"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.4360468, (float) 22.0048696);

            Winery winery9 = new Winery("Винарија „Попов“", types1, "Сопот, Општина Кавадарци", cityRepository.findByName("Кавадарци"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.4915359, (float) 22.0213826);
            Winery winery10 = new Winery("Семејната винарија „Томов“", types2, "Тимјаник", cityRepository.findByName("Неготино"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.4676689, (float) 22.0844239);

            wineryList.add(winery1);
            wineryList.add(winery2);
            wineryList.add(winery3);
            wineryList.add(winery4);
            wineryList.add(winery5);
            wineryList.add(winery6);
            wineryList.add(winery7);
            wineryList.add(winery8);
            wineryList.add(winery9);
            wineryList.add(winery10);

            wineryRepository.saveAll(wineryList);
        }
    }
}
