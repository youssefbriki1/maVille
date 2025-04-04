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

import ca.umontreal.ift2255.groupe2.maville_backend.model.Intervenant;
import ca.umontreal.ift2255.groupe2.maville_backend.model.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.model.Resident;

/**
 * Contrôleur pour la gestion des inscriptions d'utilisateurs via l'API REST.
 * Cette classe permet aux utilisateurs de s'inscrire en fournissant leurs informations
 * personnelles. Les données sont stockées dans un fichier JSON.
 */
@RestController
@RequestMapping("/api/signup")
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_FILE = "users.json";

    /**
     * Vérifie si un utilisateur est unique dans la liste des utilisateurs.
     *
     * @param personne L'objet {@link Personne} représentant l'utilisateur à vérifier.
     * @param users La liste existante des utilisateurs.
     * @return {@code true} si l'utilisateur est unique, sinon {@code false}.
     */
    private static boolean uniqueUser(Personne personne, List<Personne> users){
        for (Personne user : users) {
            if (user.getEmail().equals(personne.getEmail()) && personne.getRole().equals(user.getRole())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Point de terminaison pour inscrire un nouvel utilisateur.
     * 
     * @param personne Les données de l'utilisateur, passées dans le corps de la requête.
     * Les données incluent le nom, l'email, le mot de passe, et d'autres informations personnelles.
     * 
     * @return {@link ResponseEntity} contenant un message de succès ou une erreur
     * en fonction du résultat de l'opération.
     */
    @PostMapping
    public ResponseEntity<?> signup(@RequestBody HashMap<String, Object> personne) {
        String name = (String) personne.get("name");
        String email = (String) personne.get("email");
        String password = (String) personne.get("password");
        String phoneNumber = (String) personne.get("phone");
        String address = (String) personne.get("address");
        String postalCode = (String) personne.get("postal_code");
        String birthDate = (String) personne.get("birth_date");
        String role = (String) personne.get("role");
        Personne user;

        logger.info("Received signup request for email: {}", email);
        logger.info("Received signup request for role: {}", role);
        logger.info(phoneNumber, address, postalCode, birthDate);
        logger.info("Received signup request for name: {}", postalCode);
        

        if (!Personne.isValid(email, password)) {
            logger.warn("Invalid data for Resident with email: {}", email);
            return ResponseEntity.badRequest().body("Invalid data for Resident");
        }

        if (role.equals("Resident")) {
            user = (Resident) new Resident(name, email, password, phoneNumber, address, postalCode, birthDate);
        } else {
            user =(Intervenant) new Intervenant(name, email, password, 0);
        }

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

                File file = new File(directory, USERS_FILE);
                ObjectMapper objectMapper = new ObjectMapper();

                // Verifier que Unique
                Personne[] usersArray = objectMapper.readValue(file, Personne[].class);
                List<Personne> usersRead = Arrays.asList(usersArray);
                if (!uniqueUser(user, usersRead)) {
                    logger.error("User with email {} already exists", email);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
                }


                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

                List<Personne> users = new ArrayList<>();

                if (!file.exists() || file.length() == 0) {
                    logger.info("Initializing users.json file");
                    objectMapper.writeValue(file, users);
                } else {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(file);
                        if (jsonNode.isArray()) {
                            Personne[] existingUsers = objectMapper.treeToValue(jsonNode, Personne[].class);
                            users = new ArrayList<>(Arrays.asList(existingUsers));
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

                users.add(user);


                objectMapper.writeValue(file, users);
                logger.info("Resident with email {} added to users.json", email);
                return ResponseEntity.ok("Resident registered successfully");

            } catch (IOException e) {
                logger.error("Failed to write resident data to users.json", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to write resident data");
            }
        }
    }
}
