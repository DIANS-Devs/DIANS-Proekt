package wineverse.com.mk.Wineverse.model;

public class Rating {
    private Winery winery;
    private Reviews reviews;

    public Rating(Winery winery, Reviews reviews) {
        this.winery = winery;
        this.reviews = reviews;
    }
    public float calculate_rating()
    {
        return 0;
    }
}
