package com.focus.lit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfoDto {
    private int id;
    private String username;
    private String email;
}
