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
    private Float rating;
    private String content;
    private LocalDate date;

    public Review() {
    }

    public Review(User author, String content, LocalDate date) {
        //TODO for testing uncomment this
//        Id = (long) (Math.random() * 1000);
        this.author = author;
        this.content = content;
        this.date = date;
    }
}
