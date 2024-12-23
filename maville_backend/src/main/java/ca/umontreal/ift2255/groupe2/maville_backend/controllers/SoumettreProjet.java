package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ca.umontreal.ift2255.groupe2.maville_backend.model.ProjetIntervenant;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/soumettre_projet")
public class SoumettreProjet {

    private static final Logger logger = LoggerFactory.getLogger(SoumettreProjet.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_FILE = "users.json";
    private static final String PROJETS_FILE = "projets.json";



    @PostMapping
    public ResponseEntity<?> Envoyer(@RequestBody HashMap<String, String> settings) throws IOException {
        String IntervenantEmail = (String)settings.get("intervenant_email");
        String titre = (String) settings.get("titre");
        String typeTravail =(String) settings.get("type_travail"); 
        String description =  (String) settings.get("description");
        String date = (String) settings.get("date_debut");
        String status = "Prévu";




        logger.info(settings.toString());
        // Validate input

        if (!ProjetIntervenant.isValid(titre, description)){
            return ResponseEntity.badRequest().body("Donnees invalides pour creer une requete de travaux");
        }
        ProjetIntervenant travail = new ProjetIntervenant((int)(Math.random()*1000), titre, description, status, date, typeTravail, IntervenantEmail);


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
                    logger.info("Initializing projets.json file");
                    objectMapper.writeValue(file, travaux);
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
                        logger.error("Failed to read existing residents from projets.json", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to read existing residents");
                    }
                }

                travaux.add(travail);


                objectMapper.writeValue(file, travaux);
                return ResponseEntity.ok("Travail ajoute avec succes");

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to write resident data");
            }
        }

    }
    
}
