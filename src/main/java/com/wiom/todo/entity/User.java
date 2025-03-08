package com.wiom.todo.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
    @Column(unique = true, nullable = false)
    private String email;


    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {

    }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
