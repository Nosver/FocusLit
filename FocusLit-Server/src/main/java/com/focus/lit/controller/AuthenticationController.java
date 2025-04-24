package com.focus.lit.controller;


import com.focus.lit.dto.AuthenticationResponse;
import com.focus.lit.dto.ChangePasswordUserDto;
import com.focus.lit.dto.UserDto;
import com.focus.lit.model.User;
import com.focus.lit.service.impl.AuthenticationServiceImpl;
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
    public ResponseEntity<?> register(
            @RequestBody UserDto userDto) {
        try{
            if(!StringUtils.hasText(userDto.getMail()) || !StringUtils.hasText(userDto.getPassword()) || !StringUtils.hasText(userDto.getName())){
                return ResponseEntity.badRequest().body("All fields should be filled");
            }
            authService.register(userDto);
            return ResponseEntity.ok().body("User is successfully created");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/admin_only/registerAdminAndEmployee")
    public ResponseEntity<AuthenticationResponse> registerAdminAndEmployee(
            @RequestBody UserDto userDto) throws Exception {
            return ResponseEntity.ok(authService.register(userDto));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(authService.authenticate(userDto));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(null, "Authentication error", null));
        }
    }

    @PostMapping("/googleRegister")
    public ResponseEntity<AuthenticationResponse> googleRegister(@RequestBody UserDto userDto) {
        AuthenticationResponse response = authService.registerWithGoogle(userDto);
        if (response.getMessage().equals("User already exist")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }



}
