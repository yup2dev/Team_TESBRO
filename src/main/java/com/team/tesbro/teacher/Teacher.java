package com.team.tesbro.teacher;

import com.team.tesbro.academy.Academy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String teacherName;

    @Column
    private String qualifications;

    @Column
    private String awards;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @ManyToOne
    private Academy academy;

    @Column(columnDefinition = "TEXT")
    private String imgLogo;
}
