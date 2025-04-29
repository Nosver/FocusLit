package com.focus.lit.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private String message;
    private int userId;

    private String role;

    public AuthenticationResponse(String token, String message, String role, int userId) {
        this.token = token;
        this.message = message;
        this.role=role;
        this.userId = userId;
    }


}
