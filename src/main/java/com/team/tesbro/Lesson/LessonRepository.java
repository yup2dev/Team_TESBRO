package com.team.tesbro.Lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByLessonDate(LocalDate lessonDate);
    List<Lesson> findByAcademyId(Integer id);
}


