package wineverse.com.mk.Wineverse.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Reviews {
    private Winery winery;
    @Getter
    private List<String> reviews = new ArrayList<>();

    public Reviews(Winery winery) {
        this.winery = winery;
    }
    public void addReview(String review)
    {
        reviews.add(review);
    }
}
