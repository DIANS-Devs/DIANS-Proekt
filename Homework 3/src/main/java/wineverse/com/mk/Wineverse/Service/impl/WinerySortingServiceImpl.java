package wineverse.com.mk.Wineverse.Service.impl;

import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Model.Review;
import wineverse.com.mk.Wineverse.Model.SearchQuery;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Service.WinerySortingService;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class WinerySortingServiceImpl implements WinerySortingService {
    private static final double Z_SCORE_95_PERCENT = 1.96;

    private static LocalTime parseTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        return LocalTime.parse(time.toUpperCase(), formatter);
    }
    private static boolean isOpenNow(LocalTime currentTime, LocalTime openingTime, LocalTime closingTime) {
        return !currentTime.isBefore(openingTime) && !currentTime.isAfter(closingTime);
    }
    private Boolean isWineryOpen(Winery winery){
        // Example current time
        LocalTime currentTime = LocalTime.now();

        // Parse winery working time
        String[] workingHours = winery.getWorkingTime().split("–");
        LocalTime openingTime = parseTime(workingHours[0]);
        LocalTime closingTime = parseTime(workingHours[1]);

        // Check if the winery is open
        return isOpenNow(currentTime, openingTime, closingTime);
    }

    public static double calculateWilsonScore(int positiveReviews, int totalReviews) {
        double p = (double) positiveReviews / totalReviews;
        double z = Z_SCORE_95_PERCENT;

        return (p + z * z / (2 * totalReviews) - z * Math.sqrt((p * (1 - p) + z * z / (4 * totalReviews)) / totalReviews))
                / (1 + z * z / totalReviews);
    }

    private Double wineryPopularity(Winery winery){
        long positiveScores = winery.getReviews()
                .stream().filter(r -> r.getRating() >= 3.8).count();

        return calculateWilsonScore((int)positiveScores, winery.getReviews().size());
    }

    private List<Winery> sortWineriesByStatus(List<Winery> wineries) {
        return wineries.stream()
                .sorted(Comparator.comparing(winery -> isWineryOpen(winery) ? 0 : 1))
                .collect(Collectors.toList());
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
    public List<Winery> sortWineries(String sortingMethod, SearchQuery retrievedQuery) {
        List<Winery> current = retrievedQuery.getWineries();
        //TODO NOTHING WORKS GOOD...
        if(sortingMethod.equals("distance")){
            //TODO implement it
        }
        else if(sortingMethod.equals("open")){
            current = sortWineriesByStatus(current);
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
