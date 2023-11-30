package wineverse.com.mk.Wineverse.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class City {
    private final String name;
    private final List<Winery> city_wineries;

    public City(String name) {
        this.name = name;
        this.city_wineries = new ArrayList<>();
    }
    public void add_winery_in_city(Winery w)
    {
        city_wineries.add(w);
    }

}
