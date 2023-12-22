package wineverse.com.mk.Wineverse.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class UserPositionController {

    @PostMapping("/save-user-position")
    public ResponseEntity<String> saveUserPosition(@RequestBody String userPosition, HttpSession session) {
        // Process userPosition as needed (e.g., store it in a database)
        System.out.println("Received user position: " + userPosition);
        // Save it into the session
        session.setAttribute("userGeolocation", userPosition);

        // Return a response (you can customize this based on your requirements)
        return new ResponseEntity<>("User position saved successfully", HttpStatus.OK);
    }
}
