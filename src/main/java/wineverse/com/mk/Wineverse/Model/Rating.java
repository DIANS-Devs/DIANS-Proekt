package mk.com.wineverse.Model;

import lombok.Data;

@Data
public class Rating {
    private Winery winery;
    //TODO mislam tuka treba da ima lista od reviews, so ke se mapira
    // u nadvoresni klucevi u bazata, i taka ke moze da se presmeta totalRating
    // reviews.stream().map(review -> review.getRating()).average()
    // ili moze uopste da ne e poseban entited oti cuvamo u winery Reviews, samo metod da e
    private Reviews reviews;

    public Rating(Winery winery, Reviews reviews) {
        this.winery = winery;
        this.reviews = reviews;
    }

    public float calculateRating() {
        return 0;
    }
}
