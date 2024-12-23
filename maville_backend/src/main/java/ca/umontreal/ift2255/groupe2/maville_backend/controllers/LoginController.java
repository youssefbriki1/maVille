package ca.umontreal.ift2255.groupe2.maville_backend.controllers;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.model.Resident;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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

