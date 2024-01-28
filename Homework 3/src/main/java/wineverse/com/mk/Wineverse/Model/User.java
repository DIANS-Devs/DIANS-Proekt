package wineverse.com.mk.Wineverse.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "wineverse_user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;

    @Transient
    private String userLatitude = null;
    @Transient
    private String userLongitude = null;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ElementCollection
    private List<Long> favorites;

    public User(String username, String password, String name, String surname,String email, String phoneNumber, List<Long> favorites) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.favorites = favorites;
    }

    public void changeFavorite(Long wineryId){
        if (this.favorites.contains(wineryId)){
            this.favorites.remove(wineryId);
        }
        else {
            this.favorites.add(wineryId);
        }
    }
}
