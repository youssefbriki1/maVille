package ca.umontreal.ift2255.groupe2.maville_backend.model;

/**
 * Classe représentant un travail ou une requête initiée par un résident.
 * Contient des informations telles que l'identifiant, le titre, la description, le statut, 
 * la date de début, le type de travaux, et l'email de l'expéditeur.
 */
public class TravailResident{
    private int id;
    private String title;
    private String description;
    private String status;
    private String dateDebut;
    private String senderEmail;
    private String typeTravaux;
    /**
     * Constructeur par défaut.
     */
    public TravailResident() {
    }
    
    /**
     * Constructeur avec paramètres.
     * @param id L'identifiant du travail.
     * @param title Le titre du travail.
     * @param description La description du travail.
     * @param status Le statut du travail (e.g., "En cours", "Complété").
     * @param dateDebut La date de début du travail.
     * @param typeTravaux Le type de travaux.
     * @param senderEmail L'email de l'expéditeur du travail.
     */
    public TravailResident(int id, String title, String description, String status, String dateDebut, String typeTravaux, String senderEmail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dateDebut = dateDebut;
        this.typeTravaux = typeTravaux;
        this.senderEmail = senderEmail;
    }


    // Getters and Setters
    /**
     * Retourne l'identifiant du travail.
     * @return L'identifiant du travail.
     */
    public int getId() {
        return id;
    }
    /**
     * Retourne le titre du travail.
     * @return Le titre du travail.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Retourne la description du travail.
     * @return La description du travail.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Retourne le statut du travail.
     * @return Le statut du travail.
     */
    public String getStatus() {
        return status;
    }
    /**
     * Retourne la date de début du travail.
     * @return La date de début du travail.
     */
    public String getdateDebut() {
        return dateDebut;
    }
    /**
     * Retourne le type de travaux.
     * @return Le type de travaux.
     */
    public String getTypeTravaux() {
        return typeTravaux;
    }

    /**
     * Retourne l'email de l'expéditeur du travail.
     * @return L'email de l'expéditeur.
     */
    public String getsenderEmail() {
        return senderEmail;
    }
    /**
     * Définit l'identifiant du travail.
     * @param id L'identifiant du travail.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Définit le titre du travail.
     * @param title Le titre du travail.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Définit la description du travail.
     * @param description La description du travail.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Définit le statut du travail.
     * @param status Le statut du travail.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Définit le type de travaux.
     * @param typeTravaux Le type de travaux.
     */
    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }
    /**
     * Définit la date de début du travail.
     * @param dateDebut La date de début.
     */
    public void setdateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Définit l'email de l'expéditeur.
     * @param sender L'email de l'expéditeur.
     */
    public void setsenderEmail(String sender) {
        this.senderEmail = sender;
    }

       
    /**
     * Valide si un titre et une description sont valides pour un travail.
     * Un titre et une description valides ne doivent pas être nuls et leur longueur doit être supérieure à zéro.
     * @param title Le titre à valider.
     * @param description La description à valider.
     * @return true si les champs sont valides, false sinon.
     */
    public static boolean isValid(String title, String description) {
        return title != null && description != null && title.length() > 0 && description.length() > 0;
    } 
}
