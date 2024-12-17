package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.TravailResident;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/consulter_infos")
public class AfficherTravaux {
    private static final Logger logger = LoggerFactory.getLogger(AfficherTravaux.class);


    @GetMapping
    public ResponseEntity<?> Afficher(@RequestParam HashMap<String,String> user) throws IOException {
        logger.info("Data recieved" + user.toString());
        try {
            File directory = new File("data");
            File file = new File(directory, "requetes.json");
            ObjectMapper objectMapper = new ObjectMapper();
    
            if (!file.exists() || file.length() == 0) {
                throw new IOException("No users registered.");
            }
    
            TravailResident[] travauxArray = objectMapper.readValue(file, TravailResident[].class);
            List<TravailResident> travaux = Arrays.asList(travauxArray);

            List<TravailResident> responseList = new ArrayList<>();

            for (TravailResident travail : travaux) {
            
                if (user.get("role").equals("Resident") && user.get("email").equals(travail.getsenderEmail())) {
                    responseList.add(travail);
                }
                else if (user.get("role").equals("Intervenant")) {
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

    

