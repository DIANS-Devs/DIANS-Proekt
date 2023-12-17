package wineverse.com.mk.Wineverse.Service.impl;

import org.springframework.stereotype.Service;
import wineverse.com.mk.Wineverse.Model.Winery;
import wineverse.com.mk.Wineverse.Service.WinerySorting;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class WinerySortingImpl implements WinerySorting {
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

    @Override
    public List<Winery> sortWineriesByStatus(List<Winery> wineries) {
        return wineries.stream()
                .sorted(Comparator.comparing(winery -> isWineryOpen(winery) ? 0 : 1))
                .collect(Collectors.toList());
    }

    @Override
    public List<Winery> sortWineriesByRating(List<Winery> wineries) {
        return null;
    }

    @Override
    public List<Winery> sortWineriesByPopularity(List<Winery> wineries) {
        return null;
    }
}
