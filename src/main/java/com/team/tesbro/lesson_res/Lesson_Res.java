package com.team.tesbro.lesson_res;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Lesson.Lesson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Lesson_Res {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime bookDate;
    @ManyToOne
    private Academy academy;
    @ManyToOne
    private Lesson lesson;
}
