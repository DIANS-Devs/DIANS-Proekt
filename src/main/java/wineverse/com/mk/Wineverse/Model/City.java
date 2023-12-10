package wineverse.com.mk.Wineverse.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class City {
    private final String name;
    private final List<Winery> cityWineries;

    public City(String name) {
        this.name = name;
        this.cityWineries = new ArrayList<>();
    }
    public void addWineryInCity(Winery w)
    {
        cityWineries.add(w);
    }

}
