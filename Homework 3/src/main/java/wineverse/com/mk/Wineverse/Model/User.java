package wineverse.com.mk.Wineverse.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "wineverse_user")
public class User {
    @Id
    private String username;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;

    @OneToMany
    List<Winery> favorites;

    public User() {
    }

    public User(String username, String password, String name, String surname, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        favorites = new ArrayList<Winery>();
    }
}
