package ca.umontreal.ift2255.groupe2.maville_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import ca.umontreal.ift2255.groupe2.maville_backend.utils.Resident;	

// Tests de Jalal Fatouaki :
public class ResidentTest {

    Resident resident1 = new Resident("Jalal Fatouaki", "fatouaki.jalal@gmail.com", "password",
                                         "1234567890", "Test", "123 456", "2005-10-31");
    Resident resident2 = new Resident("Youssef Briki", "youssef.briki@gmail.com", "password456", 
                                          "0987654321", "X", "456 789", "2005-07-09");
    Resident resident3 = new Resident("Royann Lee", "royann.lee@gmail.com", "password789", 
                                          "0987654321", "X", "987 654", "2003-01-01");

    List<Resident> residents = Arrays.asList(resident1, resident2, resident3);

    @Test
    public void testGetPhone() {
        assertEquals(resident1.getPhoneNumber(), "1234567890");

        assertEquals(resident2.getPhoneNumber(), "0987654321");

        assertNotEquals(resident3.getPhoneNumber(), "123456789");
    }
    @Test
    public void testGetNameByEmail() {
        String name;
        
        name = Resident.getNameByEmail(residents, "fatouaki.jalal@gmail.com");
        assertEquals("Jalal Fatouaki", name);

        name = Resident.getNameByEmail(residents, "youssef.briki@gmail.com");
        assertEquals("Youssef Briki", name);

        name = Resident.getNameByEmail(residents, "nonexistent.email@gmail.com");
        assertNull(name);  
    }
    @Test
    public void testGetPassword(){
        String password;
         
        password = resident1.getPassword();
        assertEquals("password", password);

        password = resident2.getPassword();
        assertNotEquals("password123", password);

        password = resident3.getPassword();
        assertEquals("password789", password);
    }
    
    
   
}