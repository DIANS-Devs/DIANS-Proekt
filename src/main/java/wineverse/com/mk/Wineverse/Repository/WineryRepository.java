package wineverse.com.mk.Wineverse.Repository;

import org.springframework.stereotype.Repository;
import wineverse.com.mk.Wineverse.Data.InMemoryDataHolder;
import wineverse.com.mk.Wineverse.Model.Winery;

import java.util.List;

@Repository
public class WineryRepository {
    public List<Winery> getAllWineries() {
        return InMemoryDataHolder.wineryList;
    }
}
