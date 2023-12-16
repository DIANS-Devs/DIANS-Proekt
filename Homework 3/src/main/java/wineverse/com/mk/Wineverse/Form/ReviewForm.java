package wineverse.com.mk.Wineverse.Form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReviewForm {
    private Long wineryId;
    private int rating;
    private String comment;
    ReviewForm(){};
}
