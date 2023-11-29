package wineverse.com.mk.Wineverse.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Winery {

    private long ID;
    private String name;
    private String address;
    private String work_time;
    private int distance;
    private City city;
    private Reviews winary_reviews;
    private String winary_url;
    private String google_maps_id;
    private String phone_number;
    private String international_phone_number;
    //private String opening_hours;
    private Rating rating;
    private int totalReviewers;
    private float Latitude;
    private float Longitude;


    public long getId() {
        return ID;
    }

    public Winery(String name, String address, String work_time, City city,
                  String winary_url, String google_maps_id, String phone_number, String international_phone_number
            , float Latitude, float Longitude) {
        ID = (long) (Math.random() * 1000);
        this.name = name;
        this.address = address;
        this.work_time = work_time;
        //this.distance = distance;
        this.city = city;
        winary_reviews = new Reviews(this);
        this.winary_url = winary_url;
        this.google_maps_id = google_maps_id;
        this.phone_number = phone_number;
        this.international_phone_number = international_phone_number;
        //this.opening_hours = opening_hours;
        totalReviewers = winary_reviews.getReviews().size();
        rating = new Rating(this, winary_reviews);
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
    public void add_review(String review)
    {
        winary_reviews.addReview(review);
    }
    public List<String> get_winary_reviews()
    {
        return winary_reviews.getReviews();
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWork_time() {
        return work_time;
    }

    public int getDistance() {
        return distance;
    }

    public City getCity() {
        return city;
    }

    public Reviews getWinary_reviews() {
        return winary_reviews;
    }
    public float getWinary_rating(){
        return rating.calculate_rating();
    }
}
