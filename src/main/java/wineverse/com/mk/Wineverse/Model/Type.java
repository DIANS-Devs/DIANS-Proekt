package wineverse.com.mk.Wineverse.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String type;
    public Type() {
    }

    public Type(String type) {
        //TODO For testing uncomment this
//        Id = (long) (Math.random() * 1000);
        this.type = type;
    }
    
}
