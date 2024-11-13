package ca.umontreal.ift2255.groupe2.maville_backend.utils;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.regex.Pattern;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonSubTypes;

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


    

    public Personne(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public Personne() {
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

    public abstract String getRole();

    // Validation

    public static boolean isValid(String email, String password) {
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        
        return email != null && password != null 
                && email.length() > 0 && password.length() > 0 
                && pattern.matcher(email).matches();
    }




}