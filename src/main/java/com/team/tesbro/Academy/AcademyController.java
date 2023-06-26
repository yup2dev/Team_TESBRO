package com.team.tesbro.Academy;

import com.team.tesbro.Lesson.Lesson;
import com.team.tesbro.Lesson.LessonService;
import com.team.tesbro.Review.Review;
import com.team.tesbro.Review.ReviewService;
import com.team.tesbro.Teacher.Teacher;
import com.team.tesbro.Teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


}
