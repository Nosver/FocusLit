package com.focus.lit.controller;


import com.focus.lit.dto.AuthenticationResponse;
import com.focus.lit.dto.ChangePasswordUserDto;
import com.focus.lit.dto.MessageResponse;
import com.focus.lit.dto.UserDto;
import com.focus.lit.model.User;
import com.focus.lit.service.impl.AuthenticationServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
public class AuthenticationController {

    private final AuthenticationServiceImpl authService;

    public AuthenticationController(AuthenticationServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(
            @RequestBody UserDto userDto) {
        try{
            MessageResponse authenticationResponse = authService.register(userDto);
            if(!authenticationResponse.getMessage().equals("User registration was successful")){
                return ResponseEntity.badRequest().body(authenticationResponse);
            }
            return ResponseEntity.ok().body(authenticationResponse);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDto userDto) {
        try {
            AuthenticationResponse response = authService.authenticate(userDto);
            if (response.getToken() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse(null, e.getMessage(), null, -1));
        }
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Boolean> verifyEmail(@RequestParam String token) throws MessagingException {
        boolean res = authService.verifyEmail(token);
        if (res)
            return ResponseEntity.ok(true);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);

    }
}
