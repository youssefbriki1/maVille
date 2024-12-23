package ca.umontreal.ift2255.groupe2.maville_backend.model;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resident extends Personne implements NotificationHandler {
    private static final String role = "Resident";
    private String phoneNumber;
    private String address;
    private String postalCode;
    private String birthDate;
    private List<TravailResident> requetes;
    private List<Notification> notifications;
    private HashMap<String, Object> horraires;
    private int notificationsNumber; // Add this field
    private List<Notification> newNotifications;


    public Resident() {
        super();
    }

    public Resident(String name, String email, String password,
                    String phoneNumber, String address, String postalCode, String birthDate) { // Modifier constructeur
        super(name, email, password);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
        this.requetes = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.horraires = new HashMap<>();
    }
    


    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public static String getNameByEmail(List<Resident> residents, String email) {
        for (Resident resident : residents) {
            if (resident.getEmail().equals(email)) {
                return resident.getName(); 
            }
        }
        return null;
    }
    public HashMap<String, Object> getHorraires() {
        return horraires;
    }
    public void setHorraires(Map<String,Object> horraires) {
        System.out.println(horraires);
        this.horraires = (HashMap<String, Object>) horraires;
    }


    // Horraires

    public void updateResidentInJson() {
        try {
            File directory = new File("data");
            File file = new File(directory, "users.json");

            // Read the content of the file
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> users = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});

            // Print only the dictionaries where role is Resident
            for (Map<String, Object> user : users) {
                if ("Resident".equals(user.get("role")) && this.getEmail().equals(user.get("email"))) {
                    user.put("horraires", this.horraires);
                    mapper.writeValue(file, users);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeNotificationsInJson() {
        try {
            File directory = new File("data");
            File file = new File(directory, "users.json");
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> users = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> user : users) {
                if ("Resident".equals(user.get("role")) && this.getEmail().equals(user.get("email"))) {
                    user.put("notifications", this.notifications);
                    mapper.writeValue(file, users);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNotificationsNumber() {
        return this.notifications.size();
    }
    public void addNewNotification(Notification notification) {
        this.newNotifications.add(notification);
    }
    public void setNotificationsNumber(int notificationsNumber) {
        this.notificationsNumber = notificationsNumber;
    }
    public List<Notification> getNotifications(){
        return this.notifications;
    }

    @Override
    public List<Notification> getNewNotifications(){
        List<Notification> newNotifications = new ArrayList<>();
        for (Notification notification : this.notifications){
            if (notification.getIsNew()){
                newNotifications.add(notification);
            }
        }
        return newNotifications;
    }



    @Override
    public List<Notification> getOldNotifications(){
        List<Notification> oldNotifications = new ArrayList<>();
        for (Notification notification : this.notifications){
            if (!notification.getIsNew()){
                oldNotifications.add(notification);
            }
        }
        return oldNotifications;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public  List<TravailResident> getRequetes() {
        return requetes;
    }

    public void setRequetes(List<TravailResident> requetes) {
        this.requetes = requetes;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void deleteRequete(TravailResident requete) {
        this.requetes.remove(requete);
    }

    @Override
    public String getRole() {
        return role;
    }

    public void deleteRequete(int id) {
        for (TravailResident requete : requetes) {
            if (requete.getId() == id) {
                this.requetes.remove(requete);
                break;
            }
        }
    }

    
    public void deleteAllRequetes() {
        this.requetes.clear();
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
        writeNotificationsInJson();
    }

    public void updateRequete(TravailResident requete) {
        for (TravailResident r : requetes) {
            if (r.getId() == requete.getId()) {
                r.setTitle(requete.getTitle());
                r.setDescription(requete.getDescription());
                r.setStatus(requete.getStatus());
                r.setdateDebut(requete.getdateDebut());
                r.setsenderEmail(requete.getsenderEmail());
                break;
            }
        }
    }

    public void addRequete(TravailResident requete) {
        this.requetes.add(requete);
    }

    public void setNotificationsAsOld() {
        for (Notification notification : this.notifications) {
            notification.setIsNew(false);
        }
        writeNotificationsInJson();
    }


}