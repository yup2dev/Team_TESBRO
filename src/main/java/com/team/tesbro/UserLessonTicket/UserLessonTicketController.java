package com.team.tesbro.UserLessonTicket;

import com.team.tesbro.LessonTicket.LessonTicket;
import com.team.tesbro.LessonTicket.LessonTicketService;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserLessonTicketController {
    private final UserLessonTicketService userLessonTicketService;
    private final UserService userService;
    private final LessonTicketService lessonTicketService;

    @PostMapping("/ticket/{id}")
    public String addUserLessonTicket(@PathVariable("id") Integer ticketId, Principal principal, RedirectAttributes redirectAttributes) {
        SiteUser siteUser = userService.getUser(principal.getName());
        LessonTicket lessonTicket = lessonTicketService.getLesson(ticketId);

        if (userLessonTicketService.checkUserPayment(siteUser.getId(), lessonTicket.getId())) {
            System.out.println("이미 구매");
            redirectAttributes.addFlashAttribute("popupMessage", "이미 구매한 사용자 입니다");
            return "redirect:/ticket";
        }
        userLessonTicketService.addUserLessonTicket(siteUser, lessonTicket);
        return "redirect:/ticket/pay";
    }

    @GetMapping("/ticket")
    public String showTicket() {
        return "ticket_home";
    }
}
