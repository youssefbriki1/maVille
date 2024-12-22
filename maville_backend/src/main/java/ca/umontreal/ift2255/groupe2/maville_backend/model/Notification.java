package ca.umontreal.ift2255.groupe2.maville_backend.model;

public class Notification {
    private String title;
    private String description;
    private String date;
    private boolean isNew;

    public Notification(String title, String description, String date) throws IllegalArgumentException {
        if (!validator(title, description, date)) {
            throw new IllegalArgumentException("Invalid Notification");
        }
        this.title = title;
        this.description = description;
        this.date = date;
        this.isNew = true;
    }


    public Notification() {
    }

    // Getters and Setters

    public boolean getIsNew() {
        return isNew;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }


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
