package com.org.devops.controller;

import com.org.devops.dto.MessageRequest;
import com.org.devops.dto.MessageResponse;
import com.org.devops.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DevOpsController {
    private final JwtService jwtService;
    @Autowired
    public DevOpsController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/DevOps")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest) {
        String newJwt = jwtService.generateJwt();
        String responseMessage = "Hello " + messageRequest.getTo() + " your message will be sent";
        return ResponseEntity.ok().header("X-JWT-KWY", newJwt).body(new MessageResponse(responseMessage));
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<String> handleInvalidMethods() {
        return ResponseEntity.status(400).body("ERROR");
    }
}
