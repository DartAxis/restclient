package ru.dartinc.restclient.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;

    public AuthenticationRequestDto(String login, String password) {
        this.username = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "login'" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
