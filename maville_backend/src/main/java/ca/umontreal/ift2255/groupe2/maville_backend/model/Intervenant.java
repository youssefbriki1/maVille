package ca.umontreal.ift2255.groupe2.maville_backend.model;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Intervenant extends Personne{
    private static final String role = "Intervenant";

    private String phoneNumber;
    private String address;
    private String postalCode;
    //private String type;
    private int idCity;
    private ArrayList<TravailResident> requetes;

    public Intervenant() {
        super();
    }

    public Intervenant(String name, String email, String password, int idCity) {
        super(name, email, password);
        //this.type = type;
        this.idCity = idCity;
        this.requetes = new ArrayList<>();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
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

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public ArrayList<TravailResident> getRequetes() {
        return requetes;
    }

    public void setRequetes(ArrayList<TravailResident> requetes) {
        this.requetes = requetes;
    }



    @Override
    public String getRole() {
        return role;
    }

}
