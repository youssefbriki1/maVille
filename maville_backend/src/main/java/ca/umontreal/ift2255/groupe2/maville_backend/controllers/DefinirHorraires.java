package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Notification;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Personne;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Resident;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/definir-horraires")
public class DefinirHorraires {
    private static final Logger logger = LoggerFactory.getLogger(DefinirHorraires.class);

    @GetMapping
    public ResponseEntity<?> Afficher(@RequestParam HashMap<String,String> user) {
        logger.info("Data received1: {}", user);
        return ResponseEntity.ok("Hello");
    }

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

    public static class ScheduleRequest {
        private String resident_email;
        private List<String> days_of_week;
        private String start_time;
        private String end_time;
        private Resident resident;

        // Getters and setters

        public String getResident_email() {
            return resident_email;
        }

        public void setResident_email(String resident_email) {
            this.resident_email = resident_email;
        }

        public List<String> getDays_of_week() {
            return days_of_week;
        }

        public void setDays_of_week(List<String> days_of_week) {
            this.days_of_week = days_of_week;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
        public Resident getResident() {
            return resident;
        }
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
