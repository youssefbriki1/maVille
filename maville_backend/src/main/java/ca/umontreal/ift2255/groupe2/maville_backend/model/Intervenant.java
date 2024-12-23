package ca.umontreal.ift2255.groupe2.maville_backend.model;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe représentant un intervenant, qui est une extension de la classe {@link Personne}.
 * Un intervenant est une personne qui peut soumettre et gérer des requêtes de travaux
 * dans un système de gestion communautaire.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Intervenant extends Personne{
    private static final String role = "Intervenant";

    private String phoneNumber;
    private String address;
    private String postalCode;
    //private String type;
    private int idCity;
    private ArrayList<TravailResident> requetes;

    /**
     * Constructeur par défaut pour créer un intervenant vide.
     */
    public Intervenant() {
        super();
    }

    /**
     * Constructeur pour créer un intervenant avec des informations de base.
     * 
     * @param name     Le nom de l'intervenant.
     * @param email    L'adresse email de l'intervenant.
     * @param password Le mot de passe de l'intervenant.
     * @param idCity   L'ID de la ville associée à l'intervenant.
     */
    public Intervenant(String name, String email, String password, int idCity) {
        super(name, email, password);
        //this.type = type;
        this.idCity = idCity;
        this.requetes = new ArrayList<>();
    }

    /**
     * Obtient le numéro de téléphone de l'intervenant.
     * 
     * @return Le numéro de téléphone de l'intervenant.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Obtient l'adresse de l'intervenant.
     * 
     * @return L'adresse de l'intervenant.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Obtient le code postal de l'intervenant.
     * 
     * @return Le code postal de l'intervenant.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Définit le numéro de téléphone de l'intervenant.
     * 
     * @param phoneNumber Le numéro de téléphone à définir.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Définit l'adresse de l'intervenant.
     * 
     * @param address L'adresse à définir.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Définit le code postal de l'intervenant.
     * 
     * @param postalCode Le code postal à définir.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Obtient l'ID de la ville associée à l'intervenant.
     * 
     * @return L'ID de la ville.
     */
    public int getIdCity() {
        return idCity;
    }

    /**
     * Définit l'ID de la ville associée à l'intervenant.
     * 
     * @param idCity L'ID de la ville à définir.
     */
    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    /**
     * Obtient la liste des requêtes de travaux associées à l'intervenant.
     * 
     * @return Une liste de {@link TravailResident}.
     */
    public ArrayList<TravailResident> getRequetes() {
        return requetes;
    }

    /**
     * Définit la liste des requêtes de travaux associées à l'intervenant.
     * 
     * @param requetes La liste des requêtes de travaux à définir.
     */
    public void setRequetes(ArrayList<TravailResident> requetes) {
        this.requetes = requetes;
    }



    @Override
    public String getRole() {
        return role;
    }

}
