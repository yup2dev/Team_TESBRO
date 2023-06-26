package com.team.tesbro.Academy;

import com.team.tesbro.Lesson.Lesson;
import com.team.tesbro.Lesson.LessonService;
import com.team.tesbro.Review.Review;
import com.team.tesbro.Review.ReviewService;
import com.team.tesbro.Teacher.Teacher;
import com.team.tesbro.Teacher.TeacherService;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
@RequestMapping("/academy")
@RequiredArgsConstructor
@Controller
public class AcademyController {

    private final AcademyService academyService;
    private final ReviewService reviewService;
    private final LessonService lessonService;
    private final UserService userService;

    @RequestMapping("/list")
    public String academy(Model model, @RequestParam(value="page", defaultValue="0") int page, @Param("keyword") String keyword) {
        Page<Academy> paging = this.academyService.getAcademyList(page);
        model.addAttribute("paging", paging);

        List<Academy> academyList = this.academyService.getList(keyword);
        model.addAttribute("academyList", academyList);
        model.addAttribute("keyword", keyword);

        long count = academyService.countAcademyIds();
        model.addAttribute("academyCount", count);

        return "list";
    }

<<<<<<< HEAD

=======
    @GetMapping("/create")
    public String getCreateAcademyForm(Model model) {
        model.addAttribute("AcademyForm", new AcademyForm());
        return "academy_form";
    }


    @PostMapping("/create")
    public String AcademyCreate(@ModelAttribute AcademyForm academyForm) {
        String academyName = academyForm.getAcademyName();
        String CeoName = academyForm.getCeoName();
        String AcademyAddress = academyForm.getAcademyAddress();
        String AcademyTel = academyForm.getAcademyTel();
        String introduction = academyForm.getIntroduction();
        String imglogo = academyForm.getImgLogo();
        this.academyService.create(academyName, CeoName, AcademyAddress, AcademyTel, introduction, imglogo);
        return "redirect:/academy/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String AcademyVote(Principal principal, @PathVariable("id") Integer id) {
        Academy academy = this.academyService.getAcademy(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.academyService.vote(academy, siteUser);
        return String.format("redirect:/academy/detail/%d", id);
    }
>>>>>>> origin/lee2
}
