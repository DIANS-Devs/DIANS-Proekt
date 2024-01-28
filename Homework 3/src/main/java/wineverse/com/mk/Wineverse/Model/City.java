package wineverse.com.mk.Wineverse.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class City implements Serializable {
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
        this.wineriesInCity = new ArrayList<>();
    }

    @Override
    public String toString(){
        return name;
    }
}
