package wineverse.com.mk.Wineverse.Model;

import jakarta.persistence.*;
import lombok.Data;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Winery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    @ManyToMany
    List<Type> types;
    private String address;
    @ManyToOne
    private City city;
    private String phoneNumber;
    private String internationalPhoneNumber;
    private String workingTime;
    private String website;

    @Enumerated(EnumType.STRING)
    private OperationalStatus operationalStatus;

    private Float rating;
    private Integer totalReviews;

    @OneToMany
    List<Review> reviews;

    private boolean wheelchairAccessible;
    private Float latitude;
    private Float longitude;

    public Winery() {
    }

    public Winery(String name, List<Type> types, String address, City city,
                  String phoneNumber, String internationalPhoneNumber, String workingTime,
                  String website, OperationalStatus operationalStatus,
                  boolean wheelchairAccessible, Float latitude, Float longitude) {
        //TODO For testing uncomment this
//        Id = (long) (Math.random() * 1000);
        this.name = name;
        this.types = types;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.internationalPhoneNumber = internationalPhoneNumber;
        this.workingTime = workingTime;
        this.website = website;
        this.operationalStatus = operationalStatus;
        //TODO can be changed
        this.reviews = new ArrayList<Review>();
        this.rating = (float)0.0;
        this.totalReviews = 0;
        this.wheelchairAccessible = wheelchairAccessible;
        this.latitude = latitude;
        this.longitude = longitude;
    }
//    public Review addReview(User user, Float rating, String content, LocalDate date){
//        Review review = new Review(user, rating, content, date)
//        this.reviews.add(review);
//        return review;
//    }
}
