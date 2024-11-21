package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Intervenant;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Resident;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.TravailResident;

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
@RequestMapping("/api/envoyer_requete_resident")
@CrossOrigin(origins = "http://localhost:8501")
public class EnvoyerRequete {

    private static final Logger logger = LoggerFactory.getLogger(EnvoyerRequete.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_FILE = "requetes.json";



    @PostMapping
    public ResponseEntity<?> Envoyer(@RequestBody HashMap<String, String> settings) throws IOException {
        String ResidentEmail = (String)settings.get("resident_email");
        String titre = (String) settings.get("titre");
        String typeTravail =(String) settings.get("type_travail"); 
        String description =  (String) settings.get("description");
        String date = (String) settings.get("date_debut");
        String status = "En attente de confirmation";




        logger.info(settings.toString());
        // Validate input

        if (!TravailResident.isValid(titre, description)){
            return ResponseEntity.badRequest().body("Donnees invalides pour creer une requete de travaux");
        }
        TravailResident travail = new TravailResident((int)(Math.random()*1000), titre, description, status, date, typeTravail, ResidentEmail);


        synchronized (this) {
            try {
                // Ensure data directory exists
                File directory = new File(DATA_DIRECTORY);
                if (!directory.exists()) {
                    boolean dirCreated = directory.mkdir();
                    if (!dirCreated) {
                        logger.error("Failed to create data directory: {}", directory.getAbsolutePath());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to create data directory");
                    }
                }

                // Define JSON file
                File file = new File(directory, USERS_FILE);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing

                List<TravailResident> travaux = new ArrayList<>();

                // Initialize the file if it doesn't exist or is empty
                if (!file.exists() || file.length() == 0) {
                    logger.info("Initializing users.json file");
                    // Write an empty array to the file
                    objectMapper.writeValue(file, travaux);
                } else {
                    // Read existing data
                    try {
                        JsonNode jsonNode = objectMapper.readTree(file);
                        if (jsonNode.isArray()) {
                            TravailResident[] travauxExistants = objectMapper.treeToValue(jsonNode, TravailResident[].class);
                            travaux = new ArrayList<>(Arrays.asList(travauxExistants));
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

                // Add new resident or intervenant
                travaux.add(travail);


                // Write updated data
                objectMapper.writeValue(file, travaux);
                return ResponseEntity.ok("Travail ajoute avec succes");

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to write resident data");
            }
        }

    }
    
}
