package com.team.tesbro.board;

import com.team.tesbro.user.SiteUser;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Board board;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;
}