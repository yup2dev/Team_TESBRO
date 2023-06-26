package com.team.tesbro.Lesson;

import com.team.tesbro.lesson_res.Lesson_Res;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

