package ca.umontreal.ift2255.groupe2.maville_backend.model;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class PersonneTest {

    @Test
    void testValideAvecEmailEtMotDePasseValides() {
        String email = "utilisateur@exemple.com";
        String password = "motDePasseSecurise123";
        assertTrue(Personne.isValid(email, password), "Un email et un mot de passe valides devraient renvoyer true");
    }

    @Test
    void testInvalideAvecFormatEmailInvalide() {
        String email = "format-email-invalide";
        String password = "motdepasse123";
        assertFalse(Personne.isValid(email, password), "Un email avec un format invalide devrait renvoyer false");
    }

    @Test
    void testInvalideAvecEmailNull() {
        String email = null;
        String password = "motdepasse123";
        assertFalse(Personne.isValid(email, password), "Un email null devrait renvoyer false");
    }

    @Test
    void testInvalideAvecMotDePasseNull() {
        String email = "utilisateur@exemple.com";
        String password = null;
        assertFalse(Personne.isValid(email, password), "Un mot de passe null devrait renvoyer false");
    }

    @Test
    void testInvalideAvecEmailVide() {
        String email = "";
        String password = "motdepasse123";
        assertFalse(Personne.isValid(email, password), "Un email vide devrait renvoyer false");
    }

    @Test
    void testInvalideAvecMotDePasseVide() {
        String email = "utilisateur@exemple.com";
        String password = "";
        assertFalse(Personne.isValid(email, password), "Un mot de passe vide devrait renvoyer false");
    }

    @Test
    void testInvalideAvecEmailEspaces() {
        String email = "   ";
        String password = "motdepasse123";
        assertFalse(Personne.isValid(email, password), "Un email avec uniquement des espaces devrait renvoyer false");
    }

    @Test
    void testInvalideAvecMotDePasseEspaces() {
        String email = "utilisateur@exemple.com";
        String password = "   ";
        assertFalse(Personne.isValid(email, password), "Un mot de passe avec uniquement des espaces devrait renvoyer false");
    }

    @Test
    void testValideAvecEmailComplexeValide() {
        String email = "utilisateur.nom+alias@sous.exemple.co.uk";
        String password = "motdepasse123";
        assertTrue(Personne.isValid(email, password), "Un email complexe valide devrait renvoyer true");
    }

    @Test
    void testInvalideAvecDomaineEmailInvalide() {
        String email = "utilisateur@exemple.domaineinvalide";
        String password = "motdepasse123";
        assertFalse(Personne.isValid(email, password), "Un email avec un domaine invalide devrait renvoyer false");
    }
}
