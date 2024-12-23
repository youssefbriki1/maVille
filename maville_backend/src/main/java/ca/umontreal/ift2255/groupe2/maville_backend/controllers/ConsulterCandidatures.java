package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/get_candidatures")
public class ConsulterCandidatures {

    private static final Logger logger = LoggerFactory.getLogger(ConsulterCandidatures.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_FILE = "users.json";

    @GetMapping
    public ResponseEntity<?> getCandidatures(@RequestParam String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        File file = new File(DATA_DIRECTORY, USERS_FILE);
        ObjectMapper objectMapper = new ObjectMapper();

        if (!file.exists() || file.length() == 0) {
            logger.error("users.json file does not exist or is empty");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("users.json file does not exist or is empty");
        } else {
            try {
                JsonNode jsonNode = objectMapper.readTree(file);
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        if (email.equals(node.get("email").asText())) {
                            List<Map<String, String>> requetes = (List<Map<String, String>>) objectMapper.convertValue(node.get("requetes"), List.class);
                            return ResponseEntity.ok(requetes);
                        }
                    }
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Intervenant not found");
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
    }
    @PostMapping("/remove")
    public ResponseEntity<?> removeCandidature(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String travailId = request.get("travail_id");

        if (email == null || email.isEmpty() || travailId == null || travailId.isEmpty()) {
            return ResponseEntity.badRequest().body("Email and travail ID are required");
        }

        File file = new File(DATA_DIRECTORY, USERS_FILE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> users = new ArrayList<>();

        if (!file.exists() || file.length() == 0) {
            logger.error("users.json file does not exist or is empty");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("users.json file does not exist or is empty");
        } else {
            try {
                JsonNode jsonNode = objectMapper.readTree(file);
                boolean userFound = false;
                for (JsonNode node : jsonNode) {
                    Map<String, Object> user = objectMapper.convertValue(node, Map.class);
                    if (email.equals(user.get("email").toString())) {
                        List<Map<String, String>> requetes = (List<Map<String, String>>) user.get("requetes");
                        if (requetes != null) {
                            requetes.removeIf(requete -> travailId.equals(requete.get("id")));
                        }
                        user.put("requetes", requetes);
                        userFound = true;
                    }
                    users.add(user);
                }
                if (!userFound) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Intervenant not found");
                }
            } catch (IOException e) {
                logger.error("Failed to read existing users from users.json", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to read existing users");
            }
        }

        try {
            objectMapper.writeValue(file, users);
        } catch (IOException e) {
            logger.error("Failed to write user data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to write user data");
        }


        return ResponseEntity.ok("Candidature removed successfully");
    }
}