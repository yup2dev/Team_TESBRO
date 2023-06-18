package com.team.tesbro.lesson_res;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Lesson_ResRepository extends JpaRepository<Lesson_Res, Integer> {
    List<Lesson_Res> findByUsersId(Integer userId);
}

