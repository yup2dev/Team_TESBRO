package com.team.tesbro.Academy;

import com.team.tesbro.Teacher.Teacher;
import com.team.tesbro.Teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    @RequestMapping("/list")
    public String academy(Model model, @Param("keyword") String keyword) {
        List<Academy> academyList = this.academyService.getList(keyword);
        model.addAttribute("academyList", academyList);
        model.addAttribute("keyword", keyword);
        return "list";
    }
}
