package com.team.tesbro.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByLessonDate(LocalDate lessonDate);
    List<Lesson> findByAcademyId(Integer id);
    Optional<Lesson> findIdByLessonDateAndLessonTimeAndAcademyId(LocalDate lessonDate, LocalTime lessonTime, Integer id);
    Optional<Lesson> findById(Integer lessonId);
}

