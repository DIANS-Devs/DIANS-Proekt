package wineverse.com.mk.Wineverse.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import wineverse.com.mk.Wineverse.Model.Enumerations.OperationalStatus;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class Winery implements Serializable {
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

    @OneToMany(cascade = CascadeType.ALL)
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
        this.reviews = new ArrayList<>();
        this.rating = (float)0.0;
        this.totalReviews = 0;
        this.wheelchairAccessible = wheelchairAccessible;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Transactional
    public void addReview(Review review){
        this.reviews.add(review);
        this.rating = (float) reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        this.totalReviews = reviews.size();
    }

    public String getWineryTypesAsString(){
        if (types != null && !types.isEmpty()) {
            return types.stream().map(Type::getType).collect(Collectors.joining(", "));
        } else {
            return "No types available";
        }
    }

    public String getReviewsAsString(){
        return String.format("%.2f(%d reviews)",rating, totalReviews);
    }

}
