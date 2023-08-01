package com.team.tesbro.user_lesson_ticket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLessonTicketRepository extends JpaRepository<UserLessonTicket, Integer> {
        boolean existsBySiteUser_IdAndLessonTicket_Id(Integer userId, Integer lessonId);
        boolean existsBySiteUser_Id(Integer userId);
}
