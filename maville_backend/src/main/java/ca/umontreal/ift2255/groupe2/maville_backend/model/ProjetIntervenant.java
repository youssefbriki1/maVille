package ca.umontreal.ift2255.groupe2.maville_backend.model;

/**
 * Classe représentant un projet assigné à un intervenant.
 * Contient les informations détaillées sur le projet, y compris son titre,
 * description, statut, type, et d'autres métadonnées.
 */
public class ProjetIntervenant{
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
    public ProjetIntervenant() {
    }
    
    /**
     * Constructeur avec paramètres.
     *
     * @param id          L'identifiant unique du projet.
     * @param title       Le titre du projet.
     * @param description La description du projet.
     * @param status      Le statut actuel du projet.
     * @param dateDebut   La date de début du projet.
     * @param typeTravaux Le type de travaux associés au projet.
     * @param senderEmail L'email de l'expéditeur (responsable du projet).
     */
    public ProjetIntervenant(int id, String title, String description, String status, String dateDebut, String typeTravaux, String senderEmail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dateDebut = dateDebut;
        this.typeTravaux = typeTravaux;
        this.senderEmail = senderEmail;
    }


    /**
     * Récupère l'identifiant unique du projet.
     * @return L'identifiant du projet.
     */
    public int getId() {
        return id;
    }
    /**
     * Récupère le titre du projet.
     * @return Le titre du projet.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Récupère la description du projet.
     * @return La description du projet.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Récupère le statut actuel du projet.
     * @return Le statut du projet.
     */
    public String getStatus() {
        return status;
    }
    /**
     * Récupère la date de début du projet.
     * @return La date de début sous forme de chaîne.
     */
    public String getdateDebut() {
        return dateDebut;
    }
    /**
     * Récupère le type de travaux associés au projet.
     * @return Le type de travaux.
     */
    public String getTypeTravaux() {
        return typeTravaux;
    }

    /**
     * Récupère l'email de l'expéditeur du projet.
     * @return L'email de l'expéditeur.
     */
    public String getsenderEmail() {
        return senderEmail;
    }
    /**
     * Définit l'identifiant unique du projet.
     * @param id L'identifiant à définir.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Définit le titre du projet.
     * @param title Le titre à définir.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Définit la description du projet.
     * @param description La description à définir.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Définit le statut actuel du projet.
     * @param status Le statut à définir.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Définit le type de travaux associés au projet.
     * @param typeTravaux Le type de travaux à définir.
     */
    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }
    /**
     * Définit la date de début du projet.
     * @param dateDebut La date de début à définir.
     */
    public void setdateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Définit l'email de l'expéditeur du projet.
     * @param senderEmail L'email à définir.
     */
    public void setsenderEmail(String sender) {
        this.senderEmail = sender;
    }

    
    /**
     * Valide si le titre et la description du projet sont valides.
     *
     * @param title       Le titre à valider.
     * @param description La description à valider.
     * @return {@code true} si le titre et la description sont non nuls et non vides, sinon {@code false}.
     */
    public static boolean isValid(String title, String description) {
        return title != null && description != null && title.length() > 0 && description.length() > 0;
    } 
}
