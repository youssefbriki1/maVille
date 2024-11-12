package ca.umontreal.ift2255.groupe2.maville_backend.utils;

import ca.umontreal.ift2255.groupe2.maville_backend.utils.Requete;
import java.util.*;

public class Resident extends Personne {
    private final String role = "Resident";
    private String phoneNumber;
    private String address;
    private String postalCode;
    private String birthDate;
    private List<Requete> requetes;


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

    public  List<Requete> getRequetes() {
        return requetes;
    }

    public void setRequetes(List<Requete> requetes) {
        this.requetes = requetes;
    }

    public void deleteRequete(Requete requete) {
        this.requetes.remove(requete);
    }

    @Override
    public String getRole() {
        return role;
    }

    public void deleteRequete(int id) {
        for (Requete requete : requetes) {
            if (requete.getId() == id) {
                this.requetes.remove(requete);
                break;
            }
        }
    }

    
    public void deleteAllRequetes() {
        this.requetes.clear();
    }

    public void updateRequete(Requete requete) {
        for (Requete r : requetes) {
            if (r.getId() == requete.getId()) {
                r.setTitle(requete.getTitle());
                r.setDescription(requete.getDescription());
                r.setStatus(requete.getStatus());
                r.setDate(requete.getDate());
                r.setSender(requete.getSender());
                break;
            }
        }
    }

    public void addRequete(Requete requete) {
        this.requetes.add(requete);
    }


    // Validation


    // TODOs: Add more validation rules
    public static boolean isValid(String email, String password) {
        return email != null && password != null && email.length() > 0 && password.length() > 0;
    }


}