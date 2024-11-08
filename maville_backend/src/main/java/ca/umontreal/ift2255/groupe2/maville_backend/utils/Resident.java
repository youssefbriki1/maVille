package ca.umontreal.ift2255.groupe2.maville_backend.utils;

import java.util.Date;

public class Resident extends Person {
    private String phoneNumber;
    private String address;
    private String postalCode;
    private Date birthDate;

    public Resident(String name, String email, String password, String phoneNumber, String address, String postalCode, Date birthDate) {
        super(name, email, password);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
    }

}