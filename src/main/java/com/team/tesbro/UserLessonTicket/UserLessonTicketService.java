package com.team.tesbro.UserLessonTicket;

import com.team.tesbro.LessonTicket.LessonTicket;
import com.team.tesbro.LessonTicket.LessonTicketRepository;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.security.Principal;

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
