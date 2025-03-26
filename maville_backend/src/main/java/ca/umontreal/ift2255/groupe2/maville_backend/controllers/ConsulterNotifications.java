package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.NotificationHandler;
import ca.umontreal.ift2255.groupe2.maville_backend.model.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.model.Resident;


/**
 * Cette classe permet de récupérer les notifications (nouvelles et anciennes) pour un utilisateur résident
 * et de marquer toutes les nouvelles notifications comme lues.
 */
@RestController
@RequestMapping("/api/consulter_notifications")
public class ConsulterNotifications {
    private static final Logger logger = LoggerFactory.getLogger(ConsulterNotifications.class);

    /**
     * Récupère les notifications pour un utilisateur résident donné.
     *
     * @param user Un HashMap contenant les informations de l'utilisateur :
     *             - "email" : l'adresse email de l'utilisateur.
     * @return Une `ResponseEntity` contenant les notifications (nouvelles et anciennes) formatées,
     *         ou un message d'erreur si l'utilisateur n'est pas trouvé ou en cas de problème.
     * @throws IOException Si une erreur survient lors de la lecture du fichier `users.json`.
     */
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
            Map<String, List<Map<String,String>>> responseList = new HashMap<>();

            for (Personne personne : personnes){
                if (personne.getRole().equals("Resident") && personne.getEmail().equals(user.get("email"))){
                    Resident resident = (Resident) personne;
                    
                    responseList.put("new", NotificationHandler.formatNotificationList(resident.getNewNotifications())); // Add format
                    responseList.put("old", NotificationHandler.formatNotificationList(resident.getOldNotifications()));
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