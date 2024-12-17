package ca.umontreal.ift2255.groupe2.maville_backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.umontreal.ift2255.groupe2.maville_backend.model.Resident;
import ca.umontreal.ift2255.groupe2.maville_backend.model.TravailResident;

import static org.junit.jupiter.api.Assertions.*;

public class ResidentTest {

    private Resident resident;
    private TravailResident requete1;
    private TravailResident requete2;

    @BeforeEach
    // Pour faire en sorte de refresh avant chaque test (pour que les changements ne persistent pas)
    public void setUp() {
        resident = new Resident("Louis-Edouard Lafontant", "louis.edouard.lafontant@umontreal.ca", "password123",
                "514-000-0000", "3200 Jean Brilliant", "H3A1B2", "1990-01-01");
        requete1 = new TravailResident(1, "Requete 1", "Description 1", "Open", "2024-11-01","type1", "louis.edouard.lafontant@umontreal.ca");
        requete2 = new TravailResident(2, "Requete 2", "Description 2", "Closed", "2024-11-02", "type2", "louis.edouard.lafontant@umontreal.ca");
    }

    // Test unitaires fais par Jalal Fatouaki
    @Test
    // Test pour voir si la methode addRequete marche
    public void testAddRequete() {
        resident.addRequete(requete1);
        resident.addRequete(requete2);

        // Pour tester qu'on a add les 2 requêtes correctement
        assertEquals(2, resident.getRequetes().size());

        // Pour tester qu'il y a la premiere requete qu'on a add dans la liste des requêtes
        assertTrue(resident.getRequetes().contains(requete1));
    }

    @Test
    // Test pour voir si on peut enlever une requete parmis la liste de requetes du resident
    public void testDeleteRequeteByObject() {
        resident.addRequete(requete1);
        resident.addRequete(requete2);

        // Retrait de la requete
        resident.deleteRequete(requete1);

        // Voir s'il reste que 1 requete (requete2)
        assertEquals(1, resident.getRequetes().size());

        //Voir si requete1 n'est plus dans la liste des requete du resident
        assertFalse(resident.getRequetes().contains(requete1));
    }
    
    // Test pour voir si on peut changer les requetes
    @Test
    public void testUpdateRequete() {
        resident.addRequete(requete1);

        // Nouvelle requete
        TravailResident updatedRequete = new TravailResident(1, "Nouveau titre", "Nouvelle descrp", "Maintenance",
                "2024-11-10","type", "louis.edouard.lafontant@umontreal.ca");

        // Remplacement de la requete1 par la nouvelle (car leur id sont les memes)
        resident.updateRequete(updatedRequete);

        TravailResident result = resident.getRequetes().get(0);
        assertEquals("Nouveau titre", result.getTitle());
        assertEquals("Nouvelle descrp", result.getDescription());
        assertEquals("Maintenance", result.getStatus());
        assertEquals("2024-11-10", result.getdateDebut());
    }


    // Test unitaires fait par Royann Lee
    @Test
    public void testDeleteRequeteById() {
        // Ajouter deux requêtes au résident
        resident.addRequete(requete1);
        resident.addRequete(requete2);

        // Supprimer la requête avec l'ID 1
        resident.deleteRequete(1);

        // Vérifier que la taille de la liste des requêtes est mise à jour correctement
        assertEquals(1, resident.getRequetes().size());

        // Vérifier que la requête avec l'ID 1 a bien été supprimée
        assertFalse(resident.getRequetes().stream().anyMatch(r -> r.getId() == 1));
    }

    @Test
    public void testDeleteAllRequetes() {
        // Ajouter deux requêtes au résident
        resident.addRequete(requete1);
        resident.addRequete(requete2);

        // Supprimer toutes les requêtes du résident
        resident.deleteAllRequetes();

        // Vérifier que la liste des requêtes est vide
        assertTrue(resident.getRequetes().isEmpty());
    }

    @Test
    void testDeleteSharedRequeteDoesNotAffectOthers() {
        // Créer un autre résident
        Resident anotherResident = new Resident("Jane Doe", "jane@example.com", "password456",
                "514-555-5678", "456 Avenue", "H2B3C4", "1992-02-02");

        // Ajouter une requête partagée entre les deux résidents
        resident.addRequete(requete1);
        anotherResident.addRequete(requete1);

        // Supprimer la requête pour le premier résident
        resident.deleteRequete(requete1);

        // Vérifier que la requête partagée est toujours présente pour le second résident
        assertEquals(1, anotherResident.getRequetes().size());
    }
    
    
    

    
}