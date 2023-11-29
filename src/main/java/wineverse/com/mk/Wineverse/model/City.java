package wineverse.com.mk.Wineverse.model;

import java.util.ArrayList;
import java.util.List;

public class City {
    private final String name;
    private final List<Winery> city_wineries;

    public City(String name) {
        this.name = name;
        this.city_wineries = new ArrayList<Winery>();
    }
    public void add_winery_in_city(Winery w)
    {
        city_wineries.add(w);
    }

    public String getName() {
        return name;
    }

    public List<Winery> getCity_wineries() {
        return city_wineries;
    }
}
