package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Notification;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Resident;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/consulter-notifications")
public class ConsulterNotifications {
    private static final Logger logger = LoggerFactory.getLogger(ConsulterNotifications.class);


    @GetMapping
    public ResponseEntity<?> Afficher(@RequestParam HashMap<String,String> user) throws IOException {
        logger.info("Data received: {}", user);

        try {
            File directory = new File("data");
            File file = new File(directory, "users.json");
            ObjectMapper objectMapper = new ObjectMapper();
    
            if (!file.exists() || file.length() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users registered.");
            }
    
            Personne[] personneArray = objectMapper.readValue(file, Personne[].class);
            List<Personne> personnes = Arrays.asList(personneArray);
            List<Notification> responseList = new ArrayList<>();
    
            for (Personne personne : personnes) {
                if ("Resident".equals(user.get("role")) && user.get("email").equals(personne.getEmail())) {
                    if (personne instanceof Resident) {
                        Resident resident = (Resident) personne;
                        responseList = resident.getNotifications();
                        return ResponseEntity.ok(responseList);
                    } else {
                        logger.error("Person with email {} is not a Resident.", user.get("email"));
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not a Resident.");
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (IOException e) {
            logger.error("Error reading users from file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to read users data.");
        }
    
}
}