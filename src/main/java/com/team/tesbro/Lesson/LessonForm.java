package com.team.tesbro.Lesson;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class LessonForm {
    private Integer peopleCapacity = 0;
    @Column(length = 100)
    private String lessonName;
    private LocalDate lessonDate;
    private LocalTime lessonTime;
}
