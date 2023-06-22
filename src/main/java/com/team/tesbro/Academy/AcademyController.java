package com.team.tesbro.Academy;

import com.team.tesbro.Board.Board;
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
import java.util.List;

@RequestMapping("/academy")
@RequiredArgsConstructor
@Controller
public class AcademyController {
    private final AcademyRepository academyRepository;
    private final AcademyService academyService;
    private final TeacherService teacherService;
    private final UserService userService;

    @RequestMapping("/list")
    public String academy(Model model, @Param("keyword") String keyword) {
        List<Academy> academyList = this.academyService.getList(keyword);
        model.addAttribute("academyList", academyList);
        model.addAttribute("keyword", keyword);
        return "list";
    }

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
}
