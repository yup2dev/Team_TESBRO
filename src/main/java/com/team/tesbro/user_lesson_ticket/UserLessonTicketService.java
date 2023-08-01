package com.team.tesbro.user_lesson_ticket;

import com.team.tesbro.lesson_ticket.LessonTicket;
import com.team.tesbro.lesson_ticket.LessonTicketRepository;
import com.team.tesbro.user.SiteUser;
import com.team.tesbro.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLessonTicketService {
    private final UserLessonTicketRepository userLessonTicketRepository;
    private final UserRepository userRepository;
    private final LessonTicketRepository lessonTicketRepository;

    public UserLessonTicket addUserLessonTicket(SiteUser siteUser, LessonTicket lessonTicket) {
        UserLessonTicket userLessonTicket = new UserLessonTicket();
        userLessonTicket.setSiteUser(siteUser);
        userLessonTicket.setLessonTicket(lessonTicket);
        userLessonTicket.setUsedTicketTime(0);
        return userLessonTicketRepository.save(userLessonTicket);
    }

    public boolean checkUserPayment(Integer userId, Integer lessonId) {
        return userLessonTicketRepository.existsBySiteUser_IdAndLessonTicket_Id(userId, lessonId);
    }

    public boolean checkUserLessonTicket(Integer userId) {
        return userLessonTicketRepository.existsBySiteUser_Id(userId);
    }
}
