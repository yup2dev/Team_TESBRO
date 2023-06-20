package com.team.tesbro.lesson_res;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Lesson_ResRepository extends JpaRepository<Lesson_Res, Integer> {
    List<Lesson_Res> findByUsersId(Integer userId);
    @Query("SELECT u.id FROM Lesson_Res lr JOIN lr.users u WHERE lr.lesson.id = :lessonId")
    List<Integer> findUserIdsByLessonId(@Param("lessonId") Integer lessonId);
}

