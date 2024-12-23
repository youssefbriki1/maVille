package ca.umontreal.ift2255.groupe2.maville_backend.model;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Classe représentant un résident.
 * Hérite de {@link Personne} et implémente l'interface {@link NotificationHandler}.
 * Permet de gérer les informations personnelles, les requêtes de travaux,
 * les notifications et les horaires d'un résident.
 */
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

    /**
     * Constructeur par défaut.
     */
    public Resident() {
        super();
    }
    /**
     * Constructeur avec paramètres pour initialiser un résident.
     *
     * @param name       Le nom du résident.
     * @param email      L'adresse e-mail du résident.
     * @param password   Le mot de passe du résident.
     * @param phoneNumber Le numéro de téléphone du résident.
     * @param address    L'adresse du résident.
     * @param postalCode Le code postal du résident.
     * @param birthDate  La date de naissance du résident.
     */
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
    


    /**
     * Retourne le numéro de téléphone du résident.
     * @return Le numéro de téléphone.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Retourne l'adresse du résident.
     * @return L'adresse.
     */
    public String getAddress() {
        return address;
    }
    /**
     * Retourne le code postal du résident.
     * @return Le code postal.
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * Retourne la date de naissance du résident.
     * @return La date de naissance.
     */
    public String getBirthDate() {
        return birthDate;
    }
    /**
     * Retourne le nom d'un résident à partir de son email.
     * @param residents La liste des résidents.
     * @param email     L'email du résident.
     * @return Le nom du résident ou {@code null} si non trouvé.
     */
    public static String getNameByEmail(List<Resident> residents, String email) {
        for (Resident resident : residents) {
            if (resident.getEmail().equals(email)) {
                return resident.getName(); 
            }
        }
        return null;
    }
    /**
     * Retourne les horaires du résident.
     * @return Les horaires sous forme de {@link HashMap}.
     */
    public HashMap<String, Object> getHorraires() {
        return horraires;
    }
    /**
     * Définit les horaires du résident.
     * @param horraires Les horaires sous forme de {@link Map}.
     */
    public void setHorraires(Map<String,Object> horraires) {
        System.out.println(horraires);
        this.horraires = (HashMap<String, Object>) horraires;
    }


    // Horraires
    /**
     * Met à jour les informations du résident dans le fichier JSON.
     */
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
    /**
     * Écrit les notifications du résident dans le fichier JSON.
     */
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
    /**
     * Retourne le nombre de notifications.
     * @return Le nombre de notifications.
     */
    public int getNotificationsNumber() {
        return this.notifications.size();
    }
    /**
     * Ajoute une nouvelle notification.
     * @param notification La notification à ajouter.
     */
    public void addNewNotification(Notification notification) {
        this.newNotifications.add(notification);
    }
    /**
     * Récupère le nombre de notifications du résident.
     *
     * @param Le nombre de notifications.
     */
    public void setNotificationsNumber(int notificationsNumber) {
        this.notificationsNumber = notificationsNumber;
    }
    /**
     * Récupère la liste des notifications du résident.
     *
     * @return La liste des notifications.
     */
    public List<Notification> getNotifications(){
        return this.notifications;
    }
    /**
     * Récupère les nouvelles notifications non lues.
     *
     * @return La liste des nouvelles notifications.
     */
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


    /**
     * Récupère les anciennes notifications déjà lues.
     *
     * @return La liste des anciennes notifications.
     */
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
    /**
     * Définit le numéro de téléphone du résident.
     * @param phoneNumber Le numéro de téléphone.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * Définit l'adresse du résident.
     * @param address L'adresse.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Définit le code postal du résident.
     * @param postalCode Le code postal.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Définit la date de naissance du résident.
     * @param birthDate La date de naissance.
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    /**
     * Récupère la liste des requêtes du résident.
     *
     * @return La liste des requêtes de travail.
     */
    public  List<TravailResident> getRequetes() {
        return requetes;
    }
    /**
     * Définit la liste des requêtes de travail.
     *
     * @param requetes La liste des requêtes à définir.
     */
    public void setRequetes(List<TravailResident> requetes) {
        this.requetes = requetes;
    }
    /**
     * Définit la liste des notifications du résident.
     *
     * @param notifications La liste des notifications.
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    /**
     * Supprime une requête de travail par instance.
     *
     * @param requete La requête de travail à supprimer.
     */
    public void deleteRequete(TravailResident requete) {
        this.requetes.remove(requete);
    }
    /**
     * Retourne le rôle du résident.
     * @return Le rôle (Resident).
     */
    @Override
    public String getRole() {
        return role;
    }
    /**
     * Supprime une requête de travail par son identifiant.
     *
     * @param id L'identifiant de la requête à supprimer.
     */
    public void deleteRequete(int id) {
        for (TravailResident requete : requetes) {
            if (requete.getId() == id) {
                this.requetes.remove(requete);
                break;
            }
        }
    }

    /**
     * Supprime toutes les requêtes de travail.
     */
    public void deleteAllRequetes() {
        this.requetes.clear();
    }
    /**
     * Ajoute une nouvelle notification à la liste des notifications.
     *
     * @param notification La notification à ajouter.
     */
    public void addNotification(Notification notification) {
        this.notifications.add(notification);
        writeNotificationsInJson();
    }
    /**
     * Met à jour une requête de travail existante.
     *
     * @param requete La requête de travail mise à jour.
     */
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
    /**
     * Ajoute une nouvelle requête de travail.
     *
     * @param requete La requête de travail à ajouter.
     */
    public void addRequete(TravailResident requete) {
        this.requetes.add(requete);
    }
    /**
     * Définit toutes les notifications comme anciennes.
     */
    public void setNotificationsAsOld() {
        for (Notification notification : this.notifications) {
            notification.setIsNew(false);
        }
        writeNotificationsInJson();
    }


}
