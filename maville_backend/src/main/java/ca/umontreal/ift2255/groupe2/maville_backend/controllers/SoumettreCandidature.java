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

/**
 * Contrôleur REST pour la soumission des candidatures.
 * Ce contrôleur permet à un intervenant de soumettre une candidature pour un travail spécifique.
 */
@RestController
@RequestMapping("/api/soumettre_candidature")
public class SoumettreCandidature {

    private static final Logger logger = LoggerFactory.getLogger(SoumettreCandidature.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String REQUETES_FILE = "requetes.json";
    private static final String USERS_FILE = "users.json";

    /**
     * Endpoint POST pour soumettre une candidature pour un travail.
     * Cette méthode permet à un intervenant de soumettre une candidature pour un travail spécifié par son ID.
     * La candidature est enregistrée dans le fichier `requetes.json` et l'intervenant est mis à jour dans `users.json`.
     *
     * @param request La requête contenant les informations nécessaires pour soumettre la candidature.
     *               Elle doit inclure l'ID du travail et l'email de l'intervenant.
     * @return Une réponse HTTP avec un message de succès ou d'erreur.
     * @throws IOException Si un problème d'I/O se produit lors de la lecture ou de l'écriture des fichiers.
     */
    @PostMapping("/candidature")
    public ResponseEntity<?> soumettreCandidature(@RequestBody Map<String, String> request) throws IOException {
        String travailId = request.get("travail_id");
        String intervenantEmail = request.get("intervenant_email");

        if (travailId == null || travailId.isEmpty() || intervenantEmail == null || intervenantEmail.isEmpty()) {
            return ResponseEntity.badRequest().body("Travail ID and intervenant email are required");
        }

        logger.info("Submitting candidature: " + request);

        synchronized (this) {
            try {
                File directory = new File(DATA_DIRECTORY);
                if (!directory.exists()) {
                    boolean dirCreated = directory.mkdir();
                    if (!dirCreated) {
                        logger.error("Failed to create data directory: {}", directory.getAbsolutePath());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to create data directory");
                    }
                }

                File requetesFile = new File(directory, REQUETES_FILE);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
                List<Map<String, Object>> travaux = new ArrayList<>();

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
                                if (travailId.equals(travail.get("id").toString())) {
                                    travail.put("status", "Candidature soumise par " + intervenantEmail);
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

                // Write updated travaux back to the file
                objectMapper.writeValue(requetesFile, travaux);
                // Update the intervenant's requete list
                File usersFile = new File(directory, USERS_FILE);
                List<Map<String, Object>> users = new ArrayList<>();

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
                                if ("Intervenant".equals(node.get("role").asText()) && intervenantEmail.equals(user.get("email").toString())) {
                                    List<Map<String, String>> requetes = (List<Map<String, String>>) user.get("requetes");
                                    if (requetes == null) {
                                        requetes = new ArrayList<>();
                                        user.put("requetes", requetes);
                                    }
                                    Map<String, String> newRequete = new HashMap<>();
                                    newRequete.put("id", travailId);
                                    newRequete.put("status", "en attente d'acceptation");
                                    requetes.add(newRequete);
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

                // Write updated users back to the file
                objectMapper.writeValue(usersFile, users);

        
            } catch (IOException e) {
                logger.error("Failed to write travaux to requetes.json", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to write travaux to requetes.json");
            }
        }
        return ResponseEntity.ok("Candidature submitted successfully");
    }
    
    
}
