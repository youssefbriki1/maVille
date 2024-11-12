package ca.umontreal.ift2255.groupe2.maville_backend.utils;

public class Intervenant extends Personne{
    private String phoneNumber;
    private String address;
    private String postalCode;
    private String type;
    private int idCity;

    public Intervenant(String name, String email, String password, String type, int idCity) {
        super(name, email, password);
        this.type = type;
        this.idCity = idCity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
}
