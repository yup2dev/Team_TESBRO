package com.team.tesbro.UserLessonTicket;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.LessonTicket.LessonTicket;
import com.team.tesbro.LessonTicket.LessonTicketDto;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLessonTicketService {
    private final UserLessonTicketRepository userLessonTicketRepository;
    private final UserRepository userRepository;
    public UserLessonTicket addUserLessonTicket(SiteUser siteUser, LessonTicket lessonTicket) {
        UserLessonTicket userLessonTicket = new UserLessonTicket();
        userLessonTicket.setSiteUser(siteUser);
        userLessonTicket.setLessonTicket(lessonTicket);
        userLessonTicket.setUsedTicketTime(0);
        //여기까지 하고 쉬러간다
        return userLessonTicketRepository.save(userLessonTicket);
    }
}
