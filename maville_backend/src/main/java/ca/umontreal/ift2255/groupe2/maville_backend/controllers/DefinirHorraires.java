package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;

import ca.umontreal.ift2255.groupe2.maville_backend.model.Resident;


import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Contrôleur REST pour définir les horaires d'un résident.
 * Permet d'afficher et de configurer les horaires via des requêtes HTTP.
 */
@RestController
@RequestMapping("/api/definir-horraires")
public class DefinirHorraires {
    private static final Logger logger = LoggerFactory.getLogger(DefinirHorraires.class);

    /**
     * Endpoint GET pour afficher les informations reçues en tant que paramètres de requête.
     *
     * @param user Les données de l'utilisateur sous forme de HashMap.
     * @return Une réponse HTTP contenant un message de confirmation.
     */
    @GetMapping
    public ResponseEntity<?> Afficher(@RequestParam HashMap<String,String> user) {
        logger.info("Data received1: {}", user);
        return ResponseEntity.ok("Hello");
    }
    /**
     * Endpoint POST pour définir les horaires d'un résident.
     *
     * @param scheduleRequest Objet contenant les informations sur les horaires à configurer.
     * @return Une réponse HTTP contenant la carte des horaires configurés ou un message d'erreur approprié.
     */
    @PostMapping
    public ResponseEntity<?> definirHorraires(@RequestBody ScheduleRequest scheduleRequest) {
        logger.info("Data received2: {}", scheduleRequest);
        Map<String, Object> scheduleMap = new HashMap<>();
        scheduleMap.put("days", scheduleRequest.getDays_of_week());
        
        Map<String, String> timesMap = new HashMap<>();
        timesMap.put("start_time", scheduleRequest.getStart_time());
        timesMap.put("end_time", scheduleRequest.getEnd_time());
        
        scheduleMap.put("times", timesMap);
        Resident user = scheduleRequest.resident;
        user.setHorraires(scheduleMap);
        user.updateResidentInJson();
        return ResponseEntity.ok(scheduleMap);
        
        
    }
    /**
     * Classe interne pour encapsuler les informations sur les horaires.
     * Cette classe est utilisée pour mapper les données reçues dans les requêtes POST.
     */
    public static class ScheduleRequest {
        private String resident_email;
        private List<String> days_of_week;
        private String start_time;
        private String end_time;
        private Resident resident;

        
        /**
         * Obtient l'email du résident associé aux horaires.
         *
         * @return L'email du résident.
         */
        public String getResident_email() {
            return resident_email;
        }
        /**
         * Définit l'email du résident associé aux horaires.
         *
         * @param resident_email L'email du résident.
         */
        public void setResident_email(String resident_email) {
            this.resident_email = resident_email;
        }
        /**
         * Obtient la liste des jours de la semaine pour lesquels les horaires s'appliquent.
         *
         * @return Une liste de chaînes représentant les jours de la semaine.
         */
        public List<String> getDays_of_week() {
            return days_of_week;
        }
        /**
         * Définit la liste des jours de la semaine pour lesquels les horaires s'appliquent.
         *
         * @param days_of_week Une liste de chaînes représentant les jours de la semaine.
         */
        public void setDays_of_week(List<String> days_of_week) {
            this.days_of_week = days_of_week;
        }
        /**
         * Obtient l'heure de début des horaires configurés.
         *
         * @return Une chaîne représentant l'heure de début.
         */
        public String getStart_time() {
            return start_time;
        }
        /**
         * Définit l'heure de début des horaires configurés.
         *
         * @param start_time Une chaîne représentant l'heure de début.
         */
        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }
        /**
         * Obtient l'heure de fin des horaires configurés.
         *
         * @return Une chaîne représentant l'heure de fin.
         */
        public String getEnd_time() {
            return end_time;
        }
        /**
         * Définit l'heure de fin des horaires configurés.
         *
         * @param end_time Une chaîne représentant l'heure de fin.
         */
        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
        /**
         * Obtient le résident associé aux horaires.
         *
         * @return Un objet {@link Resident} représentant le résident.
         */
        public Resident getResident() {
            return resident;
        }
        /**
         * Définit le résident associé aux horaires.
         *
         * @param resident Un objet {@link Resident} représentant le résident.
         */
        public void setResident(Resident resident) {
            this.resident = resident;
        }

        @Override
        public String toString() {
            return "{" +
                    "resident_email='" + resident_email + '\'' +
                    ", days_of_week=" + days_of_week +
                    ", start_time='" + start_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", resident='" + resident + '\'' +
                    '}';
        }
    }
}
