package com.focus.lit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private int id;
    private String name;
    private String password;
    private String mail;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}