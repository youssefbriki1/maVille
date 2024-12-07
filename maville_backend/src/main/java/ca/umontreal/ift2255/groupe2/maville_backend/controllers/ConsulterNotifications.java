package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Resident;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ConsulterNotifications {

    // Copier afficher travaux

    private static final Logger logger = LoggerFactory.getLogger(ConsulterNotifications.class);


    @GetMapping("/consulter-notifications")
    public ResponseEntity<?> fetchData(@RequestParam HashMap<String, String> credentials) { 



        String email = credentials.get("email");
        String password = credentials.get("password");
        String role = credentials.get("role");

        if (role.equals("Intervenant")) {
            return ResponseEntity.badRequest().body("Invalid data for Resident");
        }

        
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
                            //return true;
                        } else {
                            logger.error("Role mismatch. Expected: " + role + ", Found: " + user.getRole());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    
        //return false;

}
}