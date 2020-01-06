package ru.dartinc.restclient.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class Role {

    private Long id;

    private String role;

    private Set<User> users= new HashSet<>();

    public Role(){}

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }
    public Role(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return id.equals(role1.id) &&
                role.equals(role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
