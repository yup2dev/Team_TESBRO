package com.team.tesbro.Teacher;

import com.team.tesbro.Academy.Academy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("/teacher/{id}")
    private String teacher(Model model, @PathVariable("id") Integer id){
        Teacher teacher = this.teacherService.getTeacher(id);
        model.addAttribute("teacher", teacher);
        return "teacher_detail";
    }
}
