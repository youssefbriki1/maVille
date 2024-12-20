package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.TravailResident;

@RestController
@RequestMapping("/api/futur_travaux")
public class FuturTravaux {
    private static final Logger logger = LoggerFactory.getLogger(FuturTravaux.class);

    @GetMapping
    public ResponseEntity<?> afficherTravauxProchains(@RequestParam HashMap<String, String> user) {
        logger.info("Fetching upcoming travaux for user: " + user.toString());
        try {
            File directory = new File("data");
            File file = new File(directory, "requetes.json");
            ObjectMapper objectMapper = new ObjectMapper();

            if (!file.exists() || file.length() == 0) {
                throw new IOException("No travaux data available.");
            }

            TravailResident[] travauxArray = objectMapper.readValue(file, TravailResident[].class);
            List<TravailResident> travaux = Arrays.asList(travauxArray);

            // Obtenir la date actuelle et calculer la limite de trois mois
            LocalDate currentDate = LocalDate.now();
            LocalDate threeMonthsLater = currentDate.plusMonths(3);

            List<TravailResident> travauxProchains = new ArrayList<>();

            // Filtrer les travaux pour les 3 prochains mois
            for (TravailResident travail : travaux) {
                LocalDate dateDebut = LocalDate.parse(travail.getdateDebut(), DateTimeFormatter.ISO_DATE);
                if (!dateDebut.isBefore(currentDate) && dateDebut.isBefore(threeMonthsLater)) {
                    if (user.get("role").equals("Resident") && user.get("email").equals(travail.getsenderEmail())) {
                        travauxProchains.add(travail);
                    } else if (user.get("role").equals("Intervenant")) {
                        travauxProchains.add(travail);
                    }
                }
            }

            return ResponseEntity.ok(travauxProchains);

        } catch (IOException e) {
            logger.error("Error reading travaux data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch travaux data");
        }
    }
}