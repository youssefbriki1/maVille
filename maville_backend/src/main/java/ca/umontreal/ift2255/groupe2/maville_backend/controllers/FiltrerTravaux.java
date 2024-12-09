package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.TravailResident;

@RestController
@RequestMapping("/api/filtrer_travaux")
@CrossOrigin(origins = "http://localhost:8501")
public class FiltrerTravaux {

    private static final Logger logger = LoggerFactory.getLogger(FiltrerTravaux.class);

    @GetMapping
    public ResponseEntity<?> filtrer(@RequestParam String quartier) throws IOException {
        logger.info("Quartier de recherche reçu: " + quartier);
        try {
            File directory = new File("data");
            File file = new File(directory, "requetes.json");
            ObjectMapper objectMapper = new ObjectMapper();

            if (!file.exists() || file.length() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun travail enregistré.");
            }

            TravailResident[] travauxArray = objectMapper.readValue(file, TravailResident[].class);
            List<TravailResident> travaux = Arrays.asList(travauxArray);

            List<TravailResident> travauxFiltres = new ArrayList<>();

            for (TravailResident travail : travaux) {
                if (quartier.equalsIgnoreCase(travail.getQuartier())) {
                    travauxFiltres.add(travail);
                }
            }

            return ResponseEntity.ok(travauxFiltres);

        } catch (IOException e) {
            logger.error("Erreur lors de la lecture des travaux: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur.");
        }
    }
}
