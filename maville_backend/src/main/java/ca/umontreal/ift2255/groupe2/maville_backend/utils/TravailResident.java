package ca.umontreal.ift2255.groupe2.maville_backend.utils;
public class TravailResident {
    private int id;
    private String title;
    private String description;
    private String status;
    private String dateDebut;
    private String senderEmail;
    private String typeTravaux;
    private String quartier;
    
        public TravailResident() {
        }
        
    
        public TravailResident(int id, String title, String description, String status, String dateDebut, String typeTravaux, String senderEmail, String quartier) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.status = status;
            this.dateDebut = dateDebut;
            this.typeTravaux = typeTravaux;
            this.senderEmail = senderEmail;
            this.quartier = quartier;
    }


    // Getters and Setters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getdateDebut() {
        return dateDebut;
    }
    public String getTypeTravaux() {
        return typeTravaux;
    }


    public String getsenderEmail() {
        return senderEmail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setTypeTravaux(String typeTravaux) {
        this.typeTravaux = typeTravaux;
    }

    public void setdateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }


    public void setsenderEmail(String sender) {
        this.senderEmail = sender;
    }

    // Validation

    public static boolean isValid(String title, String description) {
        return title != null && description != null && title.length() > 0 && description.length() > 0;
    }


    public String getQuartier() {
        return this.quartier;
    } 

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    
}
