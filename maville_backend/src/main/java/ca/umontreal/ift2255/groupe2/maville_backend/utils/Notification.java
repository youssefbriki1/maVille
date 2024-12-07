package ca.umontreal.ift2255.groupe2.maville_backend.utils;

public class Notification {
    private String title;
    private String description;
    private String date;
    private String type;

    public Notification(String title, String description, String date, String type) throws IllegalArgumentException {
        if (!validator(title, description, date, type)) {
            throw new IllegalArgumentException("Invalid Notification");
        }
        this.title = title;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public Notification() {
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
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

    public void setType(String type) {
        this.type = type;
    }

    public static boolean validator(String title, String description, String date, String type) {
        if (title == null || title.isEmpty()) {
            return false;
        }
        if (description == null || description.isEmpty()) {
            return false;
        }
        if (date == null || date.isEmpty()) {
            return false;
        }
        if (type == null || type.isEmpty()) {
            return false;
        }
        return true;
    }
    
}
