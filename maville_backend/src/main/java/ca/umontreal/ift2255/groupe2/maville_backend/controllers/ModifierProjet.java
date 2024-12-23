package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ca.umontreal.ift2255.groupe2.maville_backend.model.ProjetIntervenant;

/**
 * Contrôleur REST pour modifier les projets d'intervenants.
 * Cette classe permet de mettre à jour le statut d'un projet à partir de son ID.
 */
@RestController
@RequestMapping("/api/modifier_projet")
public class ModifierProjet {

    private static final Logger logger = LoggerFactory.getLogger(ModifierProjet.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String PROJETS_FILE = "projets.json";

    /**
     * Endpoint POST pour modifier le statut d'un projet.
     *
     * @param settings Un `HashMap` contenant les informations nécessaires :
     *                 - "projet_id" : L'ID du projet à modifier.
     *                 - "new_status" : Le nouveau statut à appliquer au projet.
     * @return Une `ResponseEntity` contenant un message de succès ou un message d'erreur.
     * @throws IOException Si une erreur survient lors de la lecture ou de l'écriture du fichier JSON.
     */
    @PostMapping
    public ResponseEntity<?> Modifier(@RequestBody HashMap<String, String> settings) throws IOException {
        int projetId = Integer.parseInt(settings.get("projet_id"));
        String newStatus = settings.get("new_status");

        logger.info(settings.toString());

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

                File file = new File(directory, PROJETS_FILE);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
                List<ProjetIntervenant> travaux = new ArrayList<>();

                if (!file.exists() || file.length() == 0) {
                    logger.error("projets.json file does not exist or is empty");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("projets.json file does not exist or is empty");
                } else {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(file);
                        if (jsonNode.isArray()) {
                            ProjetIntervenant[] travauxExistants = objectMapper.treeToValue(jsonNode, ProjetIntervenant[].class);
                            travaux = new ArrayList<>(Arrays.asList(travauxExistants));
                        } else {
                            logger.error("projets.json does not contain a JSON array");
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Invalid data format in projets.json");
                        }
                    } catch (IOException e) {
                        logger.error("Failed to read existing projects from projets.json", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to read existing projects");
                    }
                }

                boolean projectFound = false;
                for (ProjetIntervenant projet : travaux) {
                    if (projet.getId() == projetId) {
                        projet.setStatus(newStatus);
                        projectFound = true;
                        break;
                    }
                }

                if (!projectFound) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Project not found");
                }

                objectMapper.writeValue(file, travaux);
                return ResponseEntity.ok("Project status updated successfully");

            } catch (IOException e) {
                logger.error("Failed to write project data", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to write project data");
            }
        }
    }
}