package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/update_status")
public class UpdateStatus {

    private static final Logger logger = LoggerFactory.getLogger(UpdateStatus.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String REQUETES_FILE = "requetes.json";
    private static final String USERS_FILE = "users.json";

    @PostMapping
    public ResponseEntity<?> updateStatus(@RequestBody Map<String, String> request) {
        String requestId = request.get("request_id");
        String newStatus = request.get("new_status");

        if (requestId == null || requestId.isEmpty() || newStatus == null || newStatus.isEmpty()) {
            return ResponseEntity.badRequest().body("Request ID and new status are required");
        }

        File requetesFile = new File(DATA_DIRECTORY, REQUETES_FILE);
        File usersFile = new File(DATA_DIRECTORY, USERS_FILE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<Map<String, Object>> travaux = new ArrayList<>();
        List<Map<String, Object>> users = new ArrayList<>();

        // Update status in requetes.json
        if (!requetesFile.exists() || requetesFile.length() == 0) {
            logger.error("requetes.json file does not exist or is empty");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("requetes.json file does not exist or is empty");
        } else {
            try {
                JsonNode jsonNode = objectMapper.readTree(requetesFile);
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        Map<String, Object> travail = objectMapper.convertValue(node, Map.class);
                        if (requestId.equals(travail.get("id").toString())) {
                            travail.put("status", newStatus);
                        }
                        travaux.add(travail);
                    }
                } else {
                    logger.error("requetes.json does not contain a JSON array");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Invalid data format in requetes.json");
                }
            } catch (IOException e) {
                logger.error("Failed to read existing travaux from requetes.json", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to read existing travaux");
            }
        }

        // Update status in users.json
        if (!usersFile.exists() || usersFile.length() == 0) {
            logger.error("users.json file does not exist or is empty");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("users.json file does not exist or is empty");
        } else {
            try {
                JsonNode jsonNode = objectMapper.readTree(usersFile);
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        Map<String, Object> user = objectMapper.convertValue(node, Map.class);
                        List<Map<String, Object>> requetes = (List<Map<String, Object>>) user.get("requetes");
                        if (requetes != null) {
                            for (Map<String, Object> requete : requetes) {
                                if (requestId.equals(requete.get("id"))) {
                                    requete.put("status", newStatus);
                                }
                            }
                        }
                        users.add(user);
                    }
                } else {
                    logger.error("users.json does not contain a JSON array");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Invalid data format in users.json");
                }
            } catch (IOException e) {
                logger.error("Failed to read existing users from users.json", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to read existing users");
            }
        }

        // Write updated data back to files
        try {
            objectMapper.writeValue(requetesFile, travaux);
            objectMapper.writeValue(usersFile, users);
        } catch (IOException e) {
            logger.error("Failed to write data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to write data");
        }

        return ResponseEntity.ok("Status updated successfully");
    }
}