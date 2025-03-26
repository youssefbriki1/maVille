package ca.umontreal.ift2255.groupe2.maville_backend.controllers;



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

/**
 * Contrôleur REST pour récupérer les données des travaux et des entraves à partir d'une API externe de Montréal.
 * Ce contrôleur permet de récupérer des informations sur les travaux ou les entraves en fonction du paramètre "choix".
 */
@RestController
@RequestMapping("/api")
public class DonneesTravaux {

    private static String API_URL;
    private static final Logger logger = LoggerFactory.getLogger(DonneesTravaux.class);

    /**
     * Endpoint GET pour récupérer les données de travaux ou d'entraves à partir d'une API externe de Montréal.
     * L'API retourne un tableau JSON des enregistrements des travaux ou des entraves en fonction du paramètre "choix".
     *
     * @param choix Un paramètre de requête qui détermine le type de données à récupérer : "travaux" ou "entraves".
     * @return Une réponse HTTP contenant les données des travaux ou des entraves au format JSON.
     * @throws IllegalArgumentException Si le paramètre "choix" n'est ni "travaux" ni "entraves".
     */
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
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(API_URL, String.class);
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray records =  jsonResponse.getJSONObject("result").getJSONArray("records");
        return ResponseEntity.ok(records.toString());

    }

}
