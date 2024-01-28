package wineverse.com.mk.Wineverse.Service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.HelpServices.DistanceCalculator;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Model.SearchQuery;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Service.WinerySortingService;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WinerySortingServiceImpl implements WinerySortingService {
    private final DistanceCalculator distanceCalculator;
    private static final double Z_SCORE_95_PERCENT = 1.96;

    public static double calculateWilsonScore(int positiveReviews, int totalReviews) {
        double p = (double) positiveReviews / totalReviews;
        double z = Z_SCORE_95_PERCENT;

        return (p + z * z / q(2 * totalReviews) - z * Math.sqrt((p * (1 - p) + z * z / (4 * totalReviews)) / totalReviews))
                / (1 + z * z / totalReviews);
    }

    private Double wineryPopularity(Winery winery){
        long positiveScores = winery.getReviews()
                .stream().filter(r -> r.getRating() >= 3.8).count();

        return calculateWilsonScore((int)positiveScores, winery.getReviews().size());
    }

    private List<Winery> sortWineriesByDistance(List<Winery> wineries, String userLocation){
        wineries.sort(Comparator.comparingDouble(winery -> distanceCalculator.getDistance(userLocation, winery.getLatitude(), winery.getLongitude())));
        return wineries;
    }

    private List<Winery> sortWineriesByRating(List<Winery> wineries) {
        return wineries.stream()
                .sorted(Comparator.comparing(Winery::getRating, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private List<Winery> sortWineriesByPopularity(List<Winery> wineries) {
        return wineries.stream()
                .sorted(Comparator.comparing(this::wineryPopularity).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Winery> sortWineries(String sortingMethod, SearchQuery retrievedQuery, String userLocation) {
        List<Winery> current = retrievedQuery.getWineries();
        if(sortingMethod.equals("distance")){
            current = sortWineriesByDistance(current, userLocation);
        }
        else if(sortingMethod.equals("rating")){
            current = sortWineriesByRating(current);
        }
        else if (sortingMethod.equals("popularity")){
            current = sortWineriesByPopularity(current);
        }
        retrievedQuery.setWineries(current);
        return current;
    }
}
