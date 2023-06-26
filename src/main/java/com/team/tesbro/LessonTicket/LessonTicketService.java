package com.team.tesbro.LessonTicket;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonTicketService {
    private final LessonTicketRepository lessonTicketRepository;
    private final UserRepository userRepository;

    public void addTicketUser(Integer ticketId, Integer siteUserId, LessonTicketDto lessonTicketDto) {
        Optional<LessonTicket> lessonTicketOptional = lessonTicketRepository.findById(ticketId);
        if (lessonTicketOptional.isPresent()) {
            LessonTicket lessonTicket = lessonTicketOptional.get();
            lessonTicketDto.getUserIds().add(siteUserId);

            for (Integer users : lessonTicketDto.getUserIds()) {
                Optional<SiteUser> userOptional = userRepository.findById(users);
                SiteUser user = userOptional.orElseThrow(() -> new DataNotFoundException("존재하지 않는 사용자 ID입니다"));
                lessonTicket.getTicketUsers().add(user);
            }
            lessonTicketRepository.save(lessonTicket);
        }
    }
}

