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

}

