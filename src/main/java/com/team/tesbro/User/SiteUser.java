package com.team.tesbro.User;

import com.team.tesbro.LessonTicket.LessonTicket;
import com.team.tesbro.UserLessonTicket.UserLessonTicket;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLessonTicket> userLessonTickets = new ArrayList<>();


    public boolean isAdmin(){
        return "admin".equals(username) ||
                "admin2".equals(username) ||
                "admin3".equals(username) ||
                "admin4".equals(username);
    }
}