package ca.umontreal.ift2255.groupe2.maville_backend.controllers;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.model.Resident;

/**
 * Cette classe permet de vérifier les informations d'identification des utilisateurs
 * (email, mot de passe, rôle) et retourne une réponse indiquant si la connexion est réussie.
 * Elle gère également le calcul des notifications non lues pour les résidents.
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Endpoint POST pour authentifier un utilisateur.
     *
     * @param credentials Un HashMap contenant les informations d'identification :
     *                    - "email" : L'adresse email de l'utilisateur.
     *                    - "password" : Le mot de passe de l'utilisateur.
     *                    - "role" : Le rôle de l'utilisateur (par exemple, "Resident").
     * @return Une `ResponseEntity` indiquant si l'authentification est réussie :
     *         - Succès : Retourne un message ou le nombre de nouvelles notifications (si résident).
     *         - Échec : Retourne un message d'erreur.
     * @throws IOException Si une erreur survient lors de la lecture des données des utilisateurs.
     */
    @PostMapping
    public ResponseEntity<?> login(@RequestBody HashMap<String, String> credentials) throws IOException {
        String email = credentials.get("email");
        String password = credentials.get("password");
        String role = credentials.get("role");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }

        if (authenticateUser(email, password, role)) {
            
            if (role.equals("Resident")) {
                int notificationsNumber = getNewNotificationsNumber(email, password);
                Map<String, Integer> response = new HashMap<>();
                response.put("notificationsNumber", notificationsNumber);
                return ResponseEntity.ok(response);

            }
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    /**
     * Méthode privée pour authentifier un utilisateur.
     *
     * @param email    L'adresse email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @param role     Le rôle de l'utilisateur (par exemple, "Resident").
     * @return `true` si l'utilisateur est authentifié avec succès, `false` sinon.
     */
    private boolean authenticateUser(String email, String password, String role) {
        try {
            File directory = new File("data");
            File file = new File(directory, "users.json");
            ObjectMapper objectMapper = new ObjectMapper();
    
            if (!file.exists() || file.length() == 0) {
                throw new IOException("No users registered.");
            }
    
            Personne[] usersArray = objectMapper.readValue(file, Personne[].class);
            List<Personne> users = Arrays.asList(usersArray);
    
            for (Personne user : users) {
                if (user.getEmail().equals(email)) {
                    if (user.getPassword().equals(password)) {
                        if (role.equals(user.getRole())) {
                            logger.info("Role parameter: " + role);
                            logger.info("User's role: " + user.getRole());
                            return true;
                        } else {
                            logger.error("Role mismatch. Expected: " + role + ", Found: " + user.getRole());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    /**
     * Méthode privée pour récupérer le nombre de nouvelles notifications pour un résident.
     *
     * @param email    L'adresse email du résident.
     * @param password Le mot de passe du résident.
     * @return Le nombre de nouvelles notifications non lues, ou `-1` en cas d'erreur.
     */
    private int getNewNotificationsNumber(String email, String password){
        try {
            File directory = new File("data");
            File file = new File(directory, "users.json");
            ObjectMapper objectMapper = new ObjectMapper();
    
            if (!file.exists() || file.length() == 0) {
                throw new IOException("No users registered.");
            }
    
            Personne[] usersArray = objectMapper.readValue(file, Personne[].class);
            List<Personne> users = Arrays.asList(usersArray);
    
            for (Personne user : users) {
                if (user.getEmail().equals(email)) {
                    if (user.getPassword().equals(password)) {
                        if (user.getRole().equals("Resident")) {
                            return ((Resident) user).getNewNotifications().size();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
    }
    return -1;
    }
    
}

