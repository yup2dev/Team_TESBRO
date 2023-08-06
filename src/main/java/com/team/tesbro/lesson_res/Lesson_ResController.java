package com.team.tesbro.lesson_res;

import com.team.tesbro.lesson.Lesson;
import com.team.tesbro.lesson.LessonRepository;
import com.team.tesbro.lesson_ticket.LessonTicketService;
import com.team.tesbro.user.SiteUser;
import com.team.tesbro.user.UserRepository;
import com.team.tesbro.user.UserService;
import com.team.tesbro.user_lesson_ticket.UserLessonTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class Lesson_ResController {
    private final Lesson_ResService lesson_resService;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final UserLessonTicketService userLessonTicketService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reserve/{id}")
    public String reserveLesson(@RequestParam String datePicker,
                                @RequestParam String childSelectBox,
                                @PathVariable("id") Integer id,
                                @Valid Lesson_ResDto lessonResDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {

        // 유저 이름 필요
        //string => 날짜 시간 타입으로 변경
        LocalDate date = LocalDate.parse(datePicker, DateTimeFormatter.ISO_DATE);
        LocalTime time = LocalTime.parse(childSelectBox, DateTimeFormatter.ISO_TIME);
        // 콘솔 데이터 확인
        System.out.println(id);
        System.out.println(date);
        System.out.println(time);

        SiteUser siteUser = userService.getUser(principal.getName());

        String currentUsername = principal.getName();
        Optional<SiteUser> currentUser = userRepository.findByUsername(currentUsername);
        Integer currentUserId = currentUser.map(SiteUser::getId).orElse(null);
        lessonResDto.setBookedUsersId(Collections.singletonList(currentUserId));

        Optional<Lesson> lesson = lesson_resService.findLessonId(date, time, id);
        Integer lessonId = lesson.get().getId();
        System.out.println(lessonResDto);

        // 중복 체크 후 에러시 팝업 띄워주기
        if (!userLessonTicketService.checkUserLessonTicket(siteUser.getId())) {
            System.out.println("구매필요함");
            redirectAttributes.addFlashAttribute("popupMessage", "수강권 구매가 필요합니다");
            return "redirect:/ticket";
        } else if (lesson.get().getCurrentCapacity() >= lesson.get().getPeopleCapacity()) {
            System.out.println("꽉참");
            redirectAttributes.addFlashAttribute("popupMessage", "수용 인원을 초과했습니다");
            return "redirect:/academy/detail/{id}";
        } else if (lesson_resService.findUsers(lessonId).contains(currentUserId)) {
            System.out.println("유저 중복으로 불가능");
            redirectAttributes.addFlashAttribute("popupMessage", "이미 예약된 회원입니다");
            return "redirect:/academy/detail/{id}";
        } else {
            lesson.get().setCurrentCapacity(lesson.get().getCurrentCapacity() + 1);
            lessonRepository.save(lesson.get());
        }
        //레슨 테이블에 등록인원 추가 로직
        this.lesson_resService.reserve(lessonResDto, lessonId, currentUserId);
        System.out.println("예약됨");
        return "redirect:/reserve";
    }


    // 예약 확인 하는 부분
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reserve")
    public String onlesson(Model model, Principal principal) {
        String currentUsername = principal.getName();
        Optional<SiteUser> currentUser = userRepository.findByUsername(currentUsername);

        Integer currentUserId = currentUser.map(SiteUser::getId).orElse(null);
        List<Lesson_Res> lessonResList = lesson_resService.findUserRes(currentUserId);
        model.addAttribute("lessonResList", lessonResList);
        System.out.println(lessonResList);
        return "reserve";
    }
}
