package com.team.tesbro.Academy;

import com.team.tesbro.Teacher.Teacher;
import com.team.tesbro.Teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/academy")
@RequiredArgsConstructor
@Controller
public class AcademyController {
    private final AcademyRepository academyRepository;
    private final AcademyService academyService;
    private final TeacherService teacherService;

    @GetMapping("/list")
    public String academy(Model model) {
        List<Academy> academyList = this.academyRepository.findAll();
        model.addAttribute("academyList", academyList);
        return "list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Academy academy = this.academyService.getAcademy(id);
        model.addAttribute("academy", academy);

        return "academy_detail";
    }
}
