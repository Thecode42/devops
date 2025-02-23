package com.org.devops.controller;

import com.org.devops.dto.MessageRequest;
import com.org.devops.dto.MessageResponse;
import com.org.devops.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DevOpsControllerTest {
    @InjectMocks
    private DevOpsController devOpsController;
    @Mock
    private JwtService jwtService;

    @Test
    void testPostSendMessage() throws Exception {
        MessageRequest request = new MessageRequest();
        request.setTo("Juan Perez");
        request.setFrom("Rita Asturia");
        request.setMessage("This is a test");
        request.setTimeToLifeSec(45);

        String mockJwt = "mocked-jwt-token";
        when(jwtService.generateJwt()).thenReturn(mockJwt);

        ResponseEntity<MessageResponse> response = devOpsController.sendMessage(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hello Juan Perez your message will be sent", response.getBody().getMessage());
        assertEquals(mockJwt, response.getHeaders().getFirst("X-JWT-KWY"));
        verify(jwtService, times(1)).generateJwt();
    }

    @Test
    void testHandleInvalidMethods() {
        ResponseEntity<String> response = devOpsController.handleInvalidMethods();
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("ERROR", response.getBody());
    }
}
