package ca.umontreal.ift2255.groupe2.maville_backend.model;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * Classe abstraite représentant une personne.
 * Sert de classe parent pour les rôles spécifiques tels que {@link Resident} et {@link Intervenant}.
 * Gère les informations de base comme le nom, l'email et le mot de passe.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "role"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Resident.class, name = "Resident"),
    @JsonSubTypes.Type(value = Intervenant.class, name = "Intervenant")
})
public abstract class Personne {
    private String name;
    private String email;
    private String password;


    
    /**
     * Constructeur avec paramètres.
     *
     * @param name      Le nom de la personne.
     * @param email     L'adresse email de la personne.
     * @param password  Le mot de passe de la personne.
     */
    public Personne(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructeur par défaut.
     */
    public Personne() {
    }


    // Getters and Setters

    /**
     * Obtient le nom de la personne.
     *
     * @return Le nom de la personne.
     */
    public String getName() {
        return name;
    }
    /**
     * Obtient l'adresse email de la personne.
     *
     * @return L'adresse email de la personne.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtient le mot de passe de la personne.
     *
     * @return Le mot de passe de la personne.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Définit le nom de la personne.
     *
     * @param name Le nom de la personne.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Définit l'adresse email de la personne.
     *
     * @param email L'adresse email de la personne.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Définit le mot de passe de la personne.
     *
     * @param password Le mot de passe de la personne.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Retourne le rôle de la personne.
     * 
     * @return Le rôle de la personne (à définir dans les classes dérivées).
     */
    public abstract String getRole();

    /**
     * Valide les informations d'email et de mot de passe.
     *
     * @param email    L'adresse email à valider.
     * @param password Le mot de passe à valider.
     * @return {@code true} si l'email et le mot de passe sont valides, sinon {@code false}.
     */
    public static boolean isValid(String email, String password) {
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        
        return email != null && password != null 
                && email.length() > 0 && password.trim().length() > 0 
                && pattern.matcher(email).matches();
    }

}