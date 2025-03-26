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

import ca.umontreal.ift2255.groupe2.maville_backend.model.TravailResident;

/**
 * Cette classe permet aux résidents de soumettre une requête de travail
 * qui sera stockée dans le fichier JSON `requetes.json`.
 */
@RestController
@RequestMapping("/api/envoyer_requete_resident")
public class EnvoyerRequete {

    private static final Logger logger = LoggerFactory.getLogger(EnvoyerRequete.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_FILE = "requetes.json";


    /**
     * Endpoint POST pour soumettre une requête de travaux par un résident.
     *
     * @param settings Une HashMap contenant les détails de la requête de travail :
     *                 - "resident_email" : L'email du résident soumettant la requête.
     *                 - "titre" : Le titre du travail demandé.
     *                 - "type_travail" : Le type de travail (par exemple, réparation, rénovation).
     *                 - "description" : La description du travail demandé.
     *                 - "date_debut" : La date de début du travail.
     * @return Une `ResponseEntity` contenant un message de succès ou un message d'erreur.
     * @throws IOException Si une erreur survient lors de la lecture ou l'écriture du fichier JSON.
     */
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
                File directory = new File(DATA_DIRECTORY);
                if (!directory.exists()) {
                    boolean dirCreated = directory.mkdir();
                    if (!dirCreated) {
                        logger.error("Failed to create data directory: {}", directory.getAbsolutePath());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Failed to create data directory");
                    }
                }

                File file = new File(directory, USERS_FILE);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
                List<TravailResident> travaux = new ArrayList<>();

                if (!file.exists() || file.length() == 0) {
                    logger.info("Initializing users.json file");
                    objectMapper.writeValue(file, travaux);
                } else {
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
