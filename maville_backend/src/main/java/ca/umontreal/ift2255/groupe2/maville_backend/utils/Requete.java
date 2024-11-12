package ca.umontreal.ift2255.groupe2.maville_backend.utils;


import ca.umontreal.ift2255.groupe2.maville_backend.utils.Personne;

import java.util.*;
public class Requete {
    private int id;
    private String title;
    private String description;
    private String status;
    private String date;
    private Personne sender;


    public Requete(int id, String title, String description, String status, String date, Personne sender) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
        this.sender = sender;
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

    public String getDate() {
        return date;
    }

    public Personne getSender() {
        return sender;
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setSender(Personne sender) {
        this.sender = sender;
    }

    // Validation

    public static boolean isValid(String title, String description) {
        return title != null && description != null && title.length() > 0 && description.length() > 0;
    }




    
}
