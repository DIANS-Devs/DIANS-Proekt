package wineverse.com.mk.Wineverse.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    @OneToMany(mappedBy = "city")
    private List<Winery> wineriesInCity;

    public City() {
    }

    public City(String name) {
        this.name = name;
        this.wineriesInCity = new ArrayList<Winery>();
    }

    @Override
    public String toString(){
        return name;
    }
}
