package com.focus.lit.controller;


import com.focus.lit.dto.AuthenticationResponse;
import com.focus.lit.dto.UserDto;
import com.focus.lit.model.User;
import com.focus.lit.model.enums.Role;
import com.focus.lit.service.impl.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request) {

        return ResponseEntity.ok(authService.register(request));


    }

    @PostMapping("/admin_only/registerAdminAndEmployee")
    public ResponseEntity<AuthenticationResponse> registerAdminAndEmployee(
            @RequestBody User request) {
            return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(null, "Authentication error", null));
        }
    }

    @PostMapping("/googleRegister")
    public ResponseEntity<AuthenticationResponse> googleRegister(@RequestBody User request) {
        AuthenticationResponse response = authService.registerWithGoogle(request);
        if (response.getMessage().equals("User already exist")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

}
