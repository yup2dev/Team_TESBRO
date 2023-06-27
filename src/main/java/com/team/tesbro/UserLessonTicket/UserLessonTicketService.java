package com.team.tesbro.UserLessonTicket;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.LessonTicket.LessonTicketDto;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserRepository;

import java.util.Optional;

public class UserLessonTicketService {
    UserLessonTicketRepository userLessonTicketRepository;
    UserRepository userRepository;
    public void addTicketUser(Integer ticketId, Integer siteUserId, LessonTicketDto lessonTicketDto) {
        Optional<UserLessonTicket> ulto = userLessonTicketRepository.findById(ticketId);
        if (ulto.isPresent()) {
            UserLessonTicket UlessonTicket = ulto.get();
            lessonTicketDto.getUserIds().add(siteUserId);

            for (Integer users : lessonTicketDto.getUserIds()) {
                Optional<SiteUser> userOptional = userRepository.findById(users);
                SiteUser user = userOptional.orElseThrow(() -> new DataNotFoundException("존재하지 않는 사용자 ID입니다"));
                user = UlessonTicket.getSiteUser();
            }
            userLessonTicketRepository.save(UlessonTicket);
        }
    }
}
