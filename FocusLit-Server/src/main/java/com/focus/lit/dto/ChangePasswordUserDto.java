package com.focus.lit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangePasswordUserDto {
    private Integer id; // Optional int
    private String name;
    private String password;
    private String newPassword;
    private String mail;
}