package com.godMorning_backend.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
