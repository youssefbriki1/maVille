package ca.umontreal.ift2255.groupe2.maville_backend.utils;

import java.util.*;

public class Resident extends Personne {
    private static final String role = "Resident";
    private String phoneNumber;
    private String address;
    private String postalCode;
    private String birthDate;
    private List<TravailResident> requetes;
    private List<Notification> notifications;


    public Resident() {
        super();
    }

    public Resident(String name, String email, String password,
                    String phoneNumber, String address, String postalCode, String birthDate) {
        super(name, email, password);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
        this.requetes = new ArrayList<>();
        this.notifications = new ArrayList<>();
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

    public List<Notification> getNotifications(){
        return this.notifications;
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


    // Validation


    // TODOs: Add more validation rules
}