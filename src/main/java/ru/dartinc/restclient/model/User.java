package ru.dartinc.restclient.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Data
public class User {

    private Long id;

    private String name;

    private String login;

    private String password;

    private String address;

    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String login, String password, String address) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.address = address;
    }

    public String getUsername() {
        return login;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return " Имя пользователя: " + name +
                " id= " + id +
                " name= " + name +
                " login= " + login +
                " password= " + password +
                " address= " + address +
                " role= " + roles;
    }
}

