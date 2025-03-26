package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.ProjetIntervenant;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Contrôleur REST pour la gestion des projets.
 * Ce contrôleur permet de récupérer les projets existants depuis un fichier JSON.
 */
@RestController
@RequestMapping("/api/projets")
public class Projets {

    private static final Logger logger = LoggerFactory.getLogger(Projets.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String PROJETS_FILE = "projets.json";

    /**
     * Endpoint GET pour récupérer la liste des projets existants.
     * Cette méthode lit le fichier `projets.json` et renvoie les projets sous forme de réponse.
     *
     * @return Une réponse HTTP contenant la liste des projets existants ou un message d'erreur.
     */
    @GetMapping
    public ResponseEntity<?> getProjets() {
        File file = new File(DATA_DIRECTORY, PROJETS_FILE);
        ObjectMapper objectMapper = new ObjectMapper();
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

        return ResponseEntity.ok(travaux);
    }
}
