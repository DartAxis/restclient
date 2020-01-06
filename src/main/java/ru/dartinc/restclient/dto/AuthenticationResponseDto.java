package ru.dartinc.restclient.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {

    private String username;
    private String token;
    private String roles;


}
