package wineverse.com.mk.Wineverse.model;

import java.util.ArrayList;
import java.util.List;

public class Reviews {
    private Winery winery;
    private List<String> reviews = new ArrayList<>();

    public Reviews(Winery winery) {
        this.winery = winery;
    }
    public void addReview(String review)
    {
        reviews.add(review);
    }
    public List<String> getReviews()
    {
        return reviews;
    }
}
