package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.TravailResident;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/consulter_infos")
@CrossOrigin(origins = "http://localhost:8501")
public class AfficherTravaux {
    private static final Logger logger = LoggerFactory.getLogger(AfficherTravaux.class);


    @GetMapping
    public ResponseEntity<?> Afficher(@RequestParam String user) throws IOException {
        logger.info("Data recieved"+user);
        try {
            // Read users from users.json
            File directory = new File("data");
            File file = new File(directory, "requetes.json");
            ObjectMapper objectMapper = new ObjectMapper();
    
            if (!file.exists() || file.length() == 0) {
                // No users registered
                throw new IOException("No users registered.");
            }
    
            // Read all users as Personne[]
            TravailResident[] travauxArray = objectMapper.readValue(file, TravailResident[].class);
            List<TravailResident> travaux = Arrays.asList(travauxArray);

            List<TravailResident> responseList = new ArrayList<>();

            for (TravailResident travail : travaux) {
            
                if (!travail.getStatus().equals("En attente de confirmation") && user.equals("Resident")) {
                    responseList.add(travail);
                }
                else if (travail.getStatus().equals("En attente de confirmation") && user.equals("Intervenant")) {
                    responseList.add(travail);
                }

            }
            return ResponseEntity.ok(responseList);


        } catch (IOException e) {
            logger.error("Error reading users from file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Failed to write resident data");
}
}
}

    

