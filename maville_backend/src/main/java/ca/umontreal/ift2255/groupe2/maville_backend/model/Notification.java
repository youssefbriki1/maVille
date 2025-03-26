package ca.umontreal.ift2255.groupe2.maville_backend.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant une notification dans le système. 
 * Une notification contient un titre, une description, une date, et un statut indiquant si elle est nouvelle.
 */
public class Notification {
    private String title;
    private String description;
    private String date;
    private boolean isNew;

    /**
     * Constructeur avec paramètres. Crée une notification avec les détails spécifiés.
     * 
     * @param title       Le titre de la notification.
     * @param description La description de la notification.
     * @param date        La date de la notification.
     * @throws IllegalArgumentException Si l'une des valeurs est invalide (vide ou null).
     */
    public Notification(String title, String description, String date) throws IllegalArgumentException {
        if (!validator(title, description, date)) {
            throw new IllegalArgumentException("Invalid Notification");
        }
        this.title = title;
        this.description = description;
        this.date = date;
        this.isNew = true;
    }

    /**
     * Constructeur par défaut.
     * Permet de créer une notification vide (les champs devront être définis ultérieurement).
     */
    public Notification() {
    }

    // Getters and Setters

    /**
     * Obtient le statut indiquant si la notification est nouvelle.
     * 
     * @return True si la notification est nouvelle, False sinon.
     */
    public boolean getIsNew() {
        return isNew;
    }
    /**
     * Obtient le titre de la notification.
     * 
     * @return Le titre de la notification.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Obtient la description de la notification.
     * 
     * @return La description de la notification.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Obtient la date de la notification.
     * 
     * @return La date de la notification.
     */
    public String getDate() {
        return date;
    }

    /**
     * Définit le titre de la notification.
     * 
     * @param title Le titre à définir.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Définit la description de la notification.
     * 
     * @param description La description à définir.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Définit la date de la notification.
     * 
     * @param date La date à définir.
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * Définit le statut de la notification.
     * 
     * @param isNew True si la notification est nouvelle, False sinon.
     */
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
    /**
     * Convertit la notification en une représentation sous forme de Map.
     * 
     * @return Une Map contenant les informations formatées de la notification.
     */
    public Map<String, String> toDict(){
        Map<String, String> formatedMap= new HashMap<>();
        formatedMap.put("title", this.title);
        formatedMap.put("description", this.description);
        formatedMap.put("date", this.date);
        return formatedMap;
    }
    

    /**
     * Valide les paramètres de la notification.
     * 
     * @param title       Le titre de la notification.
     * @param description La description de la notification.
     * @param date        La date de la notification.
     * @return True si tous les paramètres sont valides, False sinon.
     */
    public static boolean validator(String title, String description, String date) {
        if (title == null || title.isEmpty()) {
            return false;
        }
        if (description == null || description.isEmpty()) {
            return false;
        }
        if (date == null || date.isEmpty()) {
            return false;
        }
        return true;
    }
    
}
