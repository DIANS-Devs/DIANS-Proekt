package wineverse.com.mk.Wineverse.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    // author == user
    @ManyToOne
    private User author;

    private Integer rating;
    private String content;
    private LocalDate date;

    public Review() {
    }

    public Review(User author, Integer rating, String content, LocalDate date) {
        this.author = author;
        this.rating = rating;
        this.content = content;
        this.date = date;
    }
}
