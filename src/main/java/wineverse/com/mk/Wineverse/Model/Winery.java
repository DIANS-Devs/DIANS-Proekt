package wineverse.com.mk.Wineverse.Model;

import lombok.Data;

import java.util.List;

@Data
public class Winery {
    private long Id;
    private String name;
    private String address;
    private String workTime;
    private int distance;
    private City city;
    // TODO mislam treba da e lista od List<Review>(), a u review da se
    //  cuva samo Author, date, text, TBD
    private Reviews wineryReviews;
    private String wineryUrl;
    private String googleMapsId;
    private String phoneNumber;
    private String internationalPhoneNumber;
    //private String opening_hours;
    private Rating rating;
    private int totalReviewers;
    private float Latitude;
    private float Longitude;

    public Winery(String name, String address, String workTime, City city,
                  String wineryUrl, String googleMapsId, String phoneNumber, String internationalPhoneNumber
            , float Latitude, float Longitude) {
        Id = (long) (Math.random() * 1000);
        this.name = name;
        this.address = address;
        this.workTime = workTime;
        //this.distance = distance;
        this.city = city;
        this.wineryReviews = new Reviews(this);
        this.wineryUrl = wineryUrl;
        this.googleMapsId = googleMapsId;
        this.phoneNumber = phoneNumber;
        this.internationalPhoneNumber = internationalPhoneNumber;
        //this.opening_hours = opening_hours;
        totalReviewers = wineryReviews.getReviews().size();
        rating = new Rating(this, wineryReviews);
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
    public void addReview(String review) {
        wineryReviews.addReview(review);
    }
    public List<String> getWineryReviews() {
        return wineryReviews.getReviews();
    }
    public float getWineryRating(){
        return rating.calculateRating();
    }
}
