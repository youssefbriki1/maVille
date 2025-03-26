package ca.umontreal.ift2255.groupe2.maville_backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConsulterCandidaturesTest {

    @Mock
    private File usersFile;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ConsulterCandidatures consulterCandidatures;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    

    @Test
    void testGetCandidatures_MissingEmail() {
        String email = "";

        ResponseEntity<?> response = consulterCandidatures.getCandidatures(email);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email is required", response.getBody());
    }



    @Test
    void testRemoveCandidature_MissingFields() {
        Map<String, String> request = new HashMap<>();
        request.put("email", "");
        request.put("travail_id", "");

        ResponseEntity<?> response = consulterCandidatures.removeCandidature(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email and travail ID are required", response.getBody());
    }


    @Test
    public void testRemoveCandidature_UserNotFound_ReturnsNotFound() throws IOException {
        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.length()).thenReturn(100L);

        JsonNode mockNode = mock(JsonNode.class);
        when(objectMapper.readTree(mockFile)).thenReturn(mockNode);
        when(mockNode.isArray()).thenReturn(true);
        when(mockNode.iterator()).thenReturn(Collections.emptyIterator());

        Map<String, String> request = new HashMap<>();
        request.put("email", "test@example.com");
        request.put("travail_id", "123");

        ResponseEntity<?> response = consulterCandidatures.removeCandidature(request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Intervenant not found", response.getBody());
    }

}