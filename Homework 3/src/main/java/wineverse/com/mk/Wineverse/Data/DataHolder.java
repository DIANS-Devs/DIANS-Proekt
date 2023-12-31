package wineverse.com.mk.Wineverse.Data;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wineverse.com.mk.Wineverse.Model.*;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;
import wineverse.com.mk.Wineverse.Repository.*;


import java.time.LocalDate;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
//        User user = new User("admin", passwordEncoder.encode( "admin"), "admin", "admin","admin@gmail.com", "074444244", new ArrayList<>());
//        User user1 = new User("user", passwordEncoder.encode("user"), "user", "user", "user@gmail.com","12213112", new ArrayList<>());
//        if(userRepository.findAll().isEmpty()) {
//            userRepository.save(user);
//            userRepository.save(user1);
//        }
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

// Add 5 manual reviews for winery1 and save them first
//            Review review1 = new Review(user, (int) 4.5f, "Excellent wine selection and a cozy atmosphere. Loved it!", LocalDate.now());
//            Review review2 = new Review(user, (int) 3.8f, "The service was good, but the wines could be better. Still a nice experience.", LocalDate.now());
//            Review review3 = new Review(user, (int) 5.0f, "Outstanding winery with top-notch wines. A must-visit!", LocalDate.now());
//            Review review4 = new Review(user, (int) 4.0f, "Great place for wine enthusiasts. Enjoyed the variety of wines available.", LocalDate.now());
//            Review review5 = new Review(user, (int) 4.2f, "Lovely winery with a beautiful setting. The staff was knowledgeable and friendly.", LocalDate.now());
//
//            winery1.addReview(review1);
//            winery1.addReview(review2);
//            winery1.addReview(review3);
//            winery1.addReview(review4);
//            winery1.addReview(review5);

            Winery winery2 = new Winery("Винарите Камник", types1, "Камник, Товарник 1, Ченто, Гази Баба, Скопје, Општина Гази Баба", cityRepository.findByName("Скопје"), "070 333 666", "+389 70 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 42.0077514, (float) 21.4902808);

// Add 5 manual reviews for winery2 and save them first
//            Review review6 = new Review(user, (int)4.6f, "Wonderful winery experience. The wines and the ambiance exceeded expectations.", LocalDate.now());
//            Review review7 = new Review(user, (int)4.2f, "Good selection of wines, and the staff was friendly. Enjoyed my visit.", LocalDate.now());
//            Review review8 = new Review(user, (int)3.5f, "Decent winery, but there is room for improvement in terms of variety.", LocalDate.now());
//            Review review9 = new Review(user, (int)4.8f, "Exceptional wines and a picturesque location. Will definitely come back.", LocalDate.now());
//            Review review10 = new Review(user, (int)4.0f, "Nice place to unwind with a glass of wine. The atmosphere is relaxing.", LocalDate.now());
//
//// Associate the reviews with winery2
//            winery2.addReview(review6);
//            winery2.addReview(review7);
//            winery2.addReview(review8);
//            winery2.addReview(review9);
//            winery2.addReview(review10);

            Winery winery3 = new Winery("Винарија со слики", types1, "67, Македонска Преродба, Оризари, Ѓорче Петров", cityRepository.findByName("Скопје"), "071 331 114", "+389 70 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 42.0094835, (float) 21.351241);

// Add 5 manual reviews for winery3 and save them first
//            Review review11 = new Review(user, (int)4.2f, "Enjoyed the wine tasting experience. The staff was informative and friendly.", LocalDate.now());
//            Review review12 = new Review(user, (int)3.8f, "Good winery, but the overall experience could be improved. Wines were okay.", LocalDate.now());
//            Review review13 = new Review(user, (int)4.5f, "Delicious wines and a charming atmosphere. A hidden gem in the region.", LocalDate.now());
//            Review review14 = new Review(user, (int)4.0f, "Decent winery with a nice selection of wines. Recommended for wine lovers.", LocalDate.now());
//            Review review15 = new Review(user, (int)4.3f, "The winery tour was informative, and the wines were of high quality.", LocalDate.now());
//
//// Associate the reviews with winery3
//            winery3.addReview(review11);
//            winery3.addReview(review12);
//            winery3.addReview(review13);
//            winery3.addReview(review14);
//            winery3.addReview(review15);

            Winery winery4 = new Winery("Зик лозја", types2, "R2247, Павлешенци, Општина Свети Николе", cityRepository.findByName("Свети Николе"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 42.0037876, (float) 21.9278854);
            Winery winery5 = new Winery("Винарија „Шато Сопот“", types1, "L10104, Новачани", cityRepository.findByName("Велес"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.7988315, (float) 21.7721987);
            Winery winery6 = new Winery("Винарија на Валанд", types2, "1 Мај, Валандово", cityRepository.findByName("Валандово"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.3195595, (float) 22.5663368);
            Winery winery7 = new Winery("Винарија Јостела", types2, "Гевгелија", cityRepository.findByName("Гевгелија"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.1355959, (float) 22.4833692);
            Winery winery8 = new Winery("Винарија и дестилерија Захарчев", types1, "Семејна винарија „Захарчев“, 16, Косовска", cityRepository.findByName("Кавадарци"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.4360468, (float) 22.0048696);

            Winery winery9 = new Winery("Винарија „Попов“", types1, "Сопот, Општина Кавадарци", cityRepository.findByName("Кавадарци"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.4915359, (float) 22.0213826);
            Winery winery10 = new Winery("Семејната винарија „Томов“", types2, "Тимјаник", cityRepository.findByName("Неготино"), "070 331 114", "+389 74 331 114", "Monday - Sunday:  7:00AM–10:00PM", "Нема", OperationalStatus.OPEN, true, (float) 41.4676689, (float) 22.0844239);

// Add 5 manual reviews for winery4 and save them first
//            Review review16 = new Review(user, (int)4.7f, "Outstanding winery with a breathtaking view. The wines were exceptional.", LocalDate.now());
//            Review review17 = new Review(user, (int)4.0f, "Nice place to explore local wines. The staff was friendly and knowledgeable.", LocalDate.now());
//            Review review18 = new Review(user, (int)4.5f, "Lovely atmosphere and delicious wines. A great spot for wine enthusiasts.", LocalDate.now());
//            Review review19 = new Review(user, (int)4.2f, "The winery tour was informative, and the wines lived up to the expectations.", LocalDate.now());
//            Review review20 = new Review(user, (int)4.8f, "Exceptional wines with a beautiful setting. Highly recommended for wine lovers.", LocalDate.now());
//
//// Associate the reviews with winery4
//            winery4.addReview(review16);
//            winery4.addReview(review17);
//            winery4.addReview(review18);
//            winery4.addReview(review19);
//            winery4.addReview(review20);

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
//
            wineryRepository.saveAll(wineryList);
        }
    }
}
