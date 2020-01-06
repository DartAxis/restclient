package ru.dartinc.restclient.dto;

import lombok.Data;
import ru.dartinc.restclient.model.User;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String password;
    private String login;
    private String address;
    private String role;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.login = user.getLogin();
        this.address = user.getAddress();
        this.role = user.getRoles().toString().replace("[]", "");

    }
}