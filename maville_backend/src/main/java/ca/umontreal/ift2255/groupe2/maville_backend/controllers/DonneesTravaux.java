package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import java.util.HashMap;

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

@RestController
@RequestMapping("/api")
public class DonneesTravaux {

    private static String API_URL;
    private static final Logger logger = LoggerFactory.getLogger(DonneesTravaux.class);

    @GetMapping("/fetch-data")
    public ResponseEntity<String> fetchData(@RequestParam String choix) { 
        logger.info("Data recieved"+choix);
        if (choix.equals("travaux")) {
            API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
        }
        else if (choix.equals("entraves")) {
            API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";                        
        }
        else {
            return ResponseEntity.badRequest().body("Invalid data for Resident");
        }
        // Use RestTemplate to make HTTP GET request
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(API_URL, String.class);

        // Parse the response using JSONObject and extract "records"
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray records =  jsonResponse.getJSONObject("result").getJSONArray("records");
        return ResponseEntity.ok(records.toString());

    }

}
