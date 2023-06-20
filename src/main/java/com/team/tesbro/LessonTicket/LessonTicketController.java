package com.team.tesbro.LessonTicket;

import com.team.tesbro.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LessonTicketController {
    private final LessonTicketService lessonTicketService;
    private final UserService userService;
    @GetMapping("/ticket/{id}")
    public String ticketa(Model model){
        return "ticket";
    }

    @PostMapping("/ticket/{id}")
    public String ticketb(Principal principal, Model model){
        return "ticket";
    }
}
