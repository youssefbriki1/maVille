package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Intervenant;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Resident;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:8501")
public class LoginController {

    @PostMapping
    public ResponseEntity<?> login(@RequestBody HashMap<String, String> credentials) throws IOException {
        String email = credentials.get("email");
        String password = credentials.get("password");
        String role = credentials.get("role");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }

        if (authenticateUser(email, password, role)) {
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
                            System.out.println("Role parameter: " + role);
                            System.out.println("User's role: " + user.getRole());
                            return true;
                        } else {
                            System.out.println("Role mismatch. Expected: " + role + ", Found: " + user.getRole());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return false;
    }
    
}
