package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.umontreal.ift2255.groupe2.maville_backend.model.ProjetIntervenant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SoumettreProjetTest {

    @Mock
    private File projetsFile;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private SoumettreProjet soumettreProjet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testEnvoyer_Success() throws IOException {
        // Arrange
        HashMap<String, String> settings = new HashMap<>();
        settings.put("intervenant_email", "larache05@example.com");
        settings.put("titre", "Nouveau Projet");
        settings.put("type_travail", "Travaux routiers");
        settings.put("description", "Description du projet");
        settings.put("date_debut", "2024-12-23");

        JsonNode projetsNode = mock(JsonNode.class);
        when(projetsFile.exists()).thenReturn(true);
        when(projetsFile.length()).thenReturn(1L);
        when(objectMapper.readTree(projetsFile)).thenReturn(projetsNode);
        when(projetsNode.isArray()).thenReturn(true);
        when(objectMapper.treeToValue(projetsNode, ProjetIntervenant[].class)).thenReturn(new ProjetIntervenant[0]);

        // Act
        ResponseEntity<?> response = soumettreProjet.Envoyer(settings);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Travail ajoute avec succes", response.getBody());
    }

    @Test
    public void testEnvoyer_InvalidProjectData() throws IOException {
        // Arrange
        HashMap<String, String> settings = new HashMap<>();

        // Act
        ResponseEntity<?> response = soumettreProjet.Envoyer(settings);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Donnees invalides pour creer une requete de travaux", response.getBody());
    }
    @Test
    public void testEnvoyer_InsufficientProjectData() throws IOException {
        // Arrange
        HashMap<String, String> settings = new HashMap<>();
        settings.put("intervenant_email", "larache05@example.com");
        settings.put("type_travail", "Travaux routiers");
        settings.put("description", "Description du projet");

        // Act
        ResponseEntity<?> response = soumettreProjet.Envoyer(settings);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Donnees invalides pour creer une requete de travaux", response.getBody());
    }

    
}