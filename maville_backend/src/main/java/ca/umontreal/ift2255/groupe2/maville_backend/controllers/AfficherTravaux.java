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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.TravailResident;

/**
 * Contrôleur REST pour consulter les informations sur les travaux.
 * Fournit une API permettant de récupérer les travaux associés à un utilisateur 
 * (résident ou intervenant) depuis un fichier JSON.
 */
@RestController
@RequestMapping("/api/consulter_infos")
public class AfficherTravaux {
    private static final Logger logger = LoggerFactory.getLogger(AfficherTravaux.class);


    /**
     * Récupère les travaux depuis un fichier JSON et filtre les données
     * en fonction du rôle et de l'email de l'utilisateur.
     *
     * @param user Un HashMap contenant les informations de l'utilisateur :
     *             - "role" : le rôle de l'utilisateur (Resident ou Intervenant)
     *             - "email" : l'email de l'utilisateur
     * @return Une ResponseEntity contenant la liste des travaux associés à l'utilisateur.
     *         Retourne une liste vide si aucun travail n'est trouvé, ou une erreur si la lecture échoue.
     * @throws IOException Si une erreur survient lors de la lecture du fichier JSON.
     */
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

    

