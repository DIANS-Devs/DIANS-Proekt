package wineverse.com.mk.Wineverse.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Reviews {
    private Winery winery;
    private List<String> reviews;
    public Reviews(Winery winery) {
        this.winery = winery;
        this.reviews = new ArrayList<>();
    }
    public void addReview(String review)
    {
        reviews.add(review);
    }
}
