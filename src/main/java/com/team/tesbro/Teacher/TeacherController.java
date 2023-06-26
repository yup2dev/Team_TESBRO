package com.team.tesbro.Teacher;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyService;
import com.team.tesbro.Review.ReviewForm;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequestMapping("/teacher")
@RequiredArgsConstructor
@Controller
public class TeacherController {
    private final TeacherService teacherService;
    private final AcademyService academyService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{id}")
    public String teacherDetail(Model model, @PathVariable("id") Integer id, @Valid TeacherForm teacherForm, BindingResult bindingResult, Principal principal) {
        Academy academy = academyService.getAcademy(id);
        model.addAttribute("academy", academy);
        model.addAttribute("teacherForm", teacherForm);
        return "teacher_detail";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String teacherCreate(Model model, @PathVariable("id") Integer id, @Valid TeacherForm teacherForm, BindingResult bindingResult, Principal principal) {
        Teacher teacher = this.teacherService.getTeacher(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Academy academy = academyService.getAcademy(id);
        teacherForm.setAcademy(academy);

        if (bindingResult.hasErrors()) {
            model.addAttribute("teacher", teacher);
            return "teacher_detail";
        }

        this.teacherService.create(teacherForm.getTeacherName(), teacherForm.getIntroduction(), teacherForm.getQualifications(),
                teacherForm.getAwards()  , teacherForm.getAcademy());
        return String.format("redirect:/academy/detail/%d", teacherForm.getAcademy().getId());
    }
}
