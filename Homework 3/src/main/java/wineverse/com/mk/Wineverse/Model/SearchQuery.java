package wineverse.com.mk.Wineverse.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchQuery implements Serializable {
    private String name;
    private Float rating ;
    private Float distance;
    private City city;
    private List<Winery> wineries = new ArrayList<>();
}
