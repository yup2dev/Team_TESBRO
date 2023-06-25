package com.team.tesbro;

import com.team.tesbro.Academy.AcademyService;
import com.team.tesbro.Board.Board;
import com.team.tesbro.Board.BoardService;
import com.team.tesbro.Review.ReviewService;
import com.team.tesbro.Teacher.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@RequiredArgsConstructor
@Controller
public class TesbroController {
    private final BoardService boardService;
    private final AcademyService academyService;
    private final TeacherService teacherService;
    private final ReviewService reviewService;

    @GetMapping("/tesbro")
    public String tesbroMain(Model model) {
        Board latestNotice = boardService.getLastestNotice();
        model.addAttribute("latestNotice", latestNotice);

        long academyCount = academyService.countAcademyIds();
        model.addAttribute("academyCount", academyCount);
        long teacherCount = teacherService.countTeacherIds();
        model.addAttribute("teacherCount",teacherCount);
        long reviewCount = reviewService.countReviewIds();
        model.addAttribute("reviewCount", reviewCount);

        return "main";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/tesbro";
    }
}
