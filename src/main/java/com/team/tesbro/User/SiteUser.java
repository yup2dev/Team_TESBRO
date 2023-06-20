package com.team.tesbro.User;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToMany(mappedBy = "ticketUsers")

    public boolean isAdmin(){
        return "admin".equals(username) ||
                "admin2".equals(username) ||
                "admin3".equals(username) ||
                "admin4".equals(username);
    }
}