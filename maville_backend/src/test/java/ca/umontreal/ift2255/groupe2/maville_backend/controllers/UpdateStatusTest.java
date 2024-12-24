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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateStatusTest {

    @Mock
    private File requetesFile;

    @Mock
    private File usersFile;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private UpdateStatus updateStatus;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateStatus_Success() throws IOException {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("request_id", "1");
        request.put("new_status", "Completed");

        JsonNode validNode = mock(JsonNode.class);
        when(requetesFile.exists()).thenReturn(true);
        when(requetesFile.length()).thenReturn(1L);
        when(objectMapper.readTree(requetesFile)).thenReturn(validNode);
        when(validNode.isArray()).thenReturn(true);

        // Act
        ResponseEntity<?> response = updateStatus.updateStatus(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status updated successfully", response.getBody());
    }

    @Test
    public void testUpdateStatus_MissingRequestId() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("request_id", "");
        request.put("new_status", "Completed");

        // Act
        ResponseEntity<?> response = updateStatus.updateStatus(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Request ID and new status are required", response.getBody());
    }
    @Test
    public void testUpdateStatus_MissingNewStatus() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("request_id", "1");
        request.put("new_status", "");

        // Act
        ResponseEntity<?> response = updateStatus.updateStatus(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Request ID and new status are required", response.getBody());
    }

    
}