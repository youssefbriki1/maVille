package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.Notification;

import java.io.File;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Contrôleur REST pour l'envoi de notifications aux résidents.
 * Ce contrôleur permet d'envoyer une notification à tous les résidents ou à un résident spécifique.
 */
@RestController
@RequestMapping("/api/envoyer_notification")
public class EnvoyerNotification {

    private static final Logger logger = LoggerFactory.getLogger(EnvoyerNotification.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_FILE = "users.json";

    /**
     * Endpoint POST pour envoyer une notification à tous les résidents.
     * Cette méthode ajoute une notification à la liste des notifications de chaque résident.
     *
     * @param request Un objet contenant les informations de la notification à envoyer : "title", "description", "date".
     * @return Une réponse HTTP indiquant si la notification a été envoyée avec succès.
     */
    @PostMapping
    public ResponseEntity<?> getResidents(@RequestBody Map<String, String> request) {
        File file = new File(DATA_DIRECTORY, USERS_FILE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> residents = new ArrayList<>();
        String title = request.get("title");
        String description = request.get("description");
        String date = request.get("date");

        if (title == null || title.isEmpty() || description == null || description.isEmpty() || date == null || date.isEmpty()) {
            return ResponseEntity.badRequest().body("Title, description, and date are required");
        }

        logger.info("Sending notification: " + request);

        Notification notification;
        try {
            notification = new Notification(title, description, date);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid notification data");
        }
        

        if (!file.exists() || file.length() == 0) {
            logger.error("users.json file does not exist or is empty");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("users.json file does not exist or is empty");
        } else {
            try {
                JsonNode jsonNode = objectMapper.readTree(file);
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        if ("Resident".equals(node.get("role").asText())) {
                            Map<String, Object> resident = objectMapper.convertValue(node, Map.class);
                            List<Map<String, String>> notifications = (List<Map<String, String>>) resident.get("notifications");
                            notifications.add(request);
                            resident.put("newNotifications", notifications);
                            Object coco = resident.get("notificationsNumber");
                            int cocoInt = (int) coco;
                            resident.put("notificationsNumber", cocoInt + 1);
                            residents.add(resident);
                            
                            
                        }
                        if ("Intervenant".equals(node.get("role").asText())) {
                            Map<String, Object> resident = objectMapper.convertValue(node, Map.class);
                            residents.add(resident);
                            
                        }
                        objectMapper.writeValue(file, residents);
                    }
                } else {
                    logger.error("users.json does not contain a JSON array");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Invalid data format in users.json");
                }
            } catch (IOException e) {
                logger.error("Failed to read existing residents from users.json", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to read existing residents");
            }
        }

        ;
        return ResponseEntity.ok("Notification sent successfully to all residents");

    }
    /**
     * Endpoint POST pour envoyer une notification à un résident spécifique en fonction de son adresse email.
     * Cette méthode ajoute une notification à la liste des notifications du résident spécifié.
     *
     * @param request Un objet contenant les informations de la notification à envoyer et l'email du résident ciblé.
     * @return Une réponse HTTP indiquant si la notification a été envoyée avec succès.
     */
    @PostMapping("/specific")
    public ResponseEntity<?> envoyerNotificationSpecifique(@RequestBody Map<String, String> request) {
        File file = new File(DATA_DIRECTORY, USERS_FILE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> residents = new ArrayList<>();
        String email = request.get("email");
        String title = request.get("title");
        String description = request.get("description");
        String date = request.get("date");

        if (email == null || email.isEmpty() || title == null || title.isEmpty() || description == null || description.isEmpty() || date == null || date.isEmpty()) {
            return ResponseEntity.badRequest().body("Email, title, description, and date are required");
        }
        

        logger.info("Sending notification: " + request);

        Notification notification;
        try {
            notification = new Notification(title, description, date);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid notification data");
        }
        

        if (!file.exists() || file.length() == 0) {
            logger.error("users.json file does not exist or is empty");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("users.json file does not exist or is empty");
        } else {
            try {
                JsonNode jsonNode = objectMapper.readTree(file);
                
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        if ("Resident".equals(node.get("role").asText()) && email.equals(node.get("email").asText())) {
                            Map<String, Object> resident = objectMapper.convertValue(node, Map.class);
                            List<Map<String, String>> notifications = (List<Map<String, String>>) resident.get("notifications");
                            request.remove("email");
                            notifications.add(request);
                            resident.put("newNotifications", notifications);
                            Object coco = resident.get("notificationsNumber");
                            int cocoInt = (int) coco;
                            resident.put("notificationsNumber", cocoInt + 1);
                            residents.add(resident);
                            
                        } else {
                            residents.add(objectMapper.convertValue(node, Map.class));
                        }
                        
                    }objectMapper.writeValue(file, residents);
                } else {
                    logger.error("users.json does not contain a JSON array");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Invalid data format in users.json");
                }
            } catch (IOException e) {
                logger.error("Failed to read existing residents from users.json", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to read existing residents");
            }
        }

        ;
        return ResponseEntity.ok("Notification sent successfully to all residents");

    }
    

}
