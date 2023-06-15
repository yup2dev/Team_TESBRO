package com.team.tesbro.lesson_res;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Lesson.Lesson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lesson_Res {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Academy academy;
    @ManyToOne
    private Lesson lesson;
}
