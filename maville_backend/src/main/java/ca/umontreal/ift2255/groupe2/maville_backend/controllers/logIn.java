package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Resident;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;



@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:8501") // Allow requests from Streamlit
public class logIn{

    private List<Object> personnes = new ArrayList<>();


    @GetMapping
    public void login(@RequestBody HashMap personne) throws IllegalArgumentException {
        String name = (String) personne.get("name");
        String email = (String) personne.get("email");
        String password = (String) personne.get("password");
        String phoneNumber = (String) personne.get("phoneNumber");
        String address = (String) personne.get("address");
        String postalCode = (String) personne.get("postalCode");
        String birthDate = (String) personne.get("birthDate");

        // Check if the data is valid
        if (Resident.isValid(email, password)){

            // Create a new Resident
            Resident resident = new Resident(name, email, password, phoneNumber, address, postalCode, birthDate);

            // Writing the resident in our json file
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("maville_backend/src/main/java/ca/umontreal/ift2255/groupe2/maville_backend/data/users.json");
            try {
                objectMapper.writeValue(file, resident);
                System.out.println("Resident added to the file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new IllegalArgumentException("Invalid Data for Resident");
        }
    }
}
