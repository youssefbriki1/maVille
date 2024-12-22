package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.Notification;
import ca.umontreal.ift2255.groupe2.maville_backend.model.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.model.Resident;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/consulter_notifications")
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
            Map<String, List<Notification>> responseList = new HashMap<>();

            for (Personne personne : personnes){
                if (personne.getRole().equals("Resident") && personne.getEmail().equals(user.get("email"))){
                    Resident resident = (Resident) personne;
                    responseList.put("new", resident.getNewNotifications());
                    responseList.put("old", resident.getOldNotifications());
                    resident.setNotificationsAsOld();
                    return ResponseEntity.ok(responseList);
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