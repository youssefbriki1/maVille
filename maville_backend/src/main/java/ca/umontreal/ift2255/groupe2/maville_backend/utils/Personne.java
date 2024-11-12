package ca.umontreal.ift2255.groupe2.maville_backend.utils;

import java.util.List;

public abstract class Personne {
    private String name;
    private String email;
    private String password;

    public Personne(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }




    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Validation

    public static boolean isValid(String email, String password) {
        return email != null && password != null && email.length() > 0 && password.length() > 0;
    }




}