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

import ca.umontreal.ift2255.groupe2.maville_backend.model.TravailResident;

@RestController
@RequestMapping("/api/futur_travaux")
public class FuturTravaux {
    private static final Logger logger = LoggerFactory.getLogger(FuturTravaux.class);

    @GetMapping
    public ResponseEntity<?> afficherTravauxProchains(@RequestParam HashMap<String, String> user) {
        logger.info("Requête reçue pour afficher les travaux à venir : " + user.toString());
        try {
            // Charger le fichier JSON contenant les travaux
            File directory = new File("data");
            File file = new File(directory, "projets.json");
            ObjectMapper objectMapper = new ObjectMapper();

            if (!file.exists() || file.length() == 0) {
                throw new IOException("Le fichier requetes.json est vide ou introuvable.");
            }

            TravailResident[] travauxArray = objectMapper.readValue(file, TravailResident[].class);
            List<TravailResident> travaux = Arrays.asList(travauxArray);

            // Calculer la plage de dates pour les 3 prochains mois
            LocalDate currentDate = LocalDate.now();
            LocalDate threeMonthsLater = currentDate.plusMonths(3);

            List<TravailResident> travauxProchains = new ArrayList<>();

            for (TravailResident travail : travaux) {
                try {
                    LocalDate dateDebut = LocalDate.parse(travail.getdateDebut(), DateTimeFormatter.ISO_DATE);
            
                    if (!dateDebut.isBefore(currentDate) && dateDebut.isBefore(threeMonthsLater)) {
                        // Ajout direct des travaux sans filtrage
                        travauxProchains.add(travail);
                    }
                } catch (Exception e) {
                    logger.warn("Skipping invalid travail entry: " + e.getMessage());
                }
            }

            return ResponseEntity.ok(travauxProchains);

        } catch (IOException e) {
            logger.error("Erreur lors de la lecture des données des travaux : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Impossible de récupérer les données des travaux.");
        }
    }
}