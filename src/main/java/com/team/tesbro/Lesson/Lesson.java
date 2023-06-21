package com.team.tesbro.Lesson;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Teacher.Teacher;
import com.team.tesbro.lesson_res.Lesson_Res;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column
    private Integer currentCapacity;
    @Column(length = 100)
    private String lessonName;
    private LocalDate lessonDate;
    private LocalTime lessonTime;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Academy academy;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.REMOVE)
    private List<Lesson_Res> lesson_resList;

}

