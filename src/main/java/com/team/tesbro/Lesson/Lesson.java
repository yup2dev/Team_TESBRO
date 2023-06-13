package com.team.tesbro.Lesson;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Teacher.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Getter
@Setter
@ToString
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer peopleCapacity;
    @Column(length = 100)
    private String lessonName;
    private LocalDate lessonDate;
    private LocalTime lessonTime;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Academy academy;
}
