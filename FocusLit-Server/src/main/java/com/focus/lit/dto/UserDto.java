package com.focus.lit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id; // Optional int
    private String name;
    private String password;
    private String mail;
}