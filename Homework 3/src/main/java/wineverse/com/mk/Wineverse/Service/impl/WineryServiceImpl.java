package wineverse.com.mk.Wineverse.Service.impl;

import lombok.AllArgsConstructor;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Repository.ReviewRepository;
import wineverse.com.mk.Wineverse.Repository.WineryRepository;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Service.WineryService;
import wineverse.com.mk.Wineverse.Model.City;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WineryServiceImpl implements WineryService {
    private final WineryRepository wineryRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<Winery> getAllWineries() {
        return wineryRepository.findAll();
    }

    @Override
    public void setNewReview(Long wineryId, Review review) {
        wineryRepository.findById(wineryId).get().addReview(review);
        reviewRepository.save(review);
    }

    @Override
    //Worst case: Name="", rating = 0, distance = 300, City = Цела Македонија
    public List<Winery> filteredWineries(String name, Float rating, Float distance, City city) {
        //TODO implement distance filter
        List<Winery> name_wineries = wineryRepository.findByNameContaining(name);
        List<Winery> rating_wineries = wineryRepository.findByRatingGreaterThanEqual(rating);
        List<Winery> city_wineries;
        if (!city.getName().equals("Цела Македонија")) {
            city_wineries = wineryRepository.findByCity(city);
        } else {
            city_wineries = wineryRepository.findAll();
        }
        List<Winery> interceptWineries = new ArrayList<>(name_wineries);
        interceptWineries.retainAll(rating_wineries);
        interceptWineries.retainAll(city_wineries);
        return interceptWineries;
    }

    @Override
    public Optional<Winery> getWineryById(Long id) {
        return wineryRepository.findById(id);
    }

    @Override
    public List<Winery> getWineriesByIds(List<String> favoriteWineryIds) {
        List<Winery> return_wineries = new ArrayList<>();
        for(Winery w: wineryRepository.findAll()){
            for(String ids : favoriteWineryIds){
                long id = Long.parseLong(ids);
                if(id == w.getId())
                    return_wineries.add(w);
            }
        }
        return return_wineries;
    }

    @Override
    public List<String> getWineriesAsString(){
        List<Winery> wineries = wineryRepository.findAll();
        return wineries.stream()
                .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFavouriteWineriesAsString() {
        //TODO NOT THIS CODE, CHANGE IT
        List<Winery> wineries = wineryRepository.findAll();
        return wineries.stream()
                .map(winery -> String.format("%d|%s|%s|%s", winery.getId(), winery.getLatitude(), winery.getLongitude(), winery.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveWinery(Winery winery) {
        wineryRepository.save(winery);
    }
}
