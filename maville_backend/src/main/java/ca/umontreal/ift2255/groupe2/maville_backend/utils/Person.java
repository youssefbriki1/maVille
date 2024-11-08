package ca.umontreal.ift2255.groupe2.maville_backend.utils;

public abstract class Person {
    private String name;
    private String email;
    private String password;

    public Person(String name, String email, String password) {
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
    
    public static boolean isValidEmail(String email) { //  TODO:  Add more email validation / if private or public 
        return email.contains("@") && email.contains(".");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private static boolean isValid() throws Value
        return isValidEmail(email) && !name.isEmpty() && isValidPassword(password);
    }

}
