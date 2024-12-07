package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Intervenant;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Resident;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/signup")
@CrossOrigin(origins = "http://localhost:8501")
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_FILE = "users.json";

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
