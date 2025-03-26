package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SoumettreCandidatureTest {

    private static final String TEST_DATA_DIRECTORY = "test_data";
    private static final String TEST_REQUETES_FILE = "test_requetes.json";
    private static final String TEST_USERS_FILE = "test_users.json";


    @Mock
    private File usersFile;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private SoumettreCandidature soumettreCandidature;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }

    @Test
    void testSuccessfulCandidatureSubmission() throws IOException {
        SoumettreCandidature controller = new SoumettreCandidature();
        
        // Create test files with valid data
        createTestFile(TEST_REQUETES_FILE, "[{\"id\": \"1\", \"status\": \"open\"}]");
        createTestFile(TEST_USERS_FILE, "[{\"email\": \"test@example.com\", \"role\": \"Intervenant\", \"requetes\": []}]");

        Map<String, String> request = new HashMap<>();
        request.put("travail_id", "1");
        request.put("intervenant_email", "test@example.com");

        ResponseEntity<?> response = controller.soumettreCandidature(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Candidature submitted successfully", response.getBody());
    }
    @Test
    public void testSoumettreCandidature_MissingRequiredFields() throws IOException {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("email", "");
        request.put("travail_id", "1");

        // Act
        ResponseEntity<?> response = soumettreCandidature.soumettreCandidature(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Travail ID and intervenant email are required", response.getBody());
    }

    @Test
    public void testSoumettreCandidature_FailureToReadOrWriteUsersFile() throws IOException {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("email", "test@example.com");
        request.put("travail_id", "1");

        when(usersFile.exists()).thenReturn(true);
        when(usersFile.length()).thenReturn(1L);
        when(objectMapper.readTree(usersFile)).thenThrow(new IOException("Failed to read file"));

        // Act
        ResponseEntity<?> response = soumettreCandidature.soumettreCandidature(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Travail ID and intervenant email are required", response.getBody());
    }
    private void createTestFile(String fileName, String content) throws IOException {
        File file = new File(TEST_DATA_DIRECTORY, fileName);
        file.getParentFile().mkdirs();
        objectMapper.writeValue(file, objectMapper.readTree(content));
    }

}
