package com.team.tesbro;

import com.team.tesbro.academy.Academy;
import com.team.tesbro.academy.AcademyService;
import com.team.tesbro.board.Board;
import com.team.tesbro.board.BoardService;
import com.team.tesbro.review.Review;
import com.team.tesbro.review.ReviewService;
import com.team.tesbro.teacher.TeacherService;
import com.team.tesbro.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TesbroController {
    private final BoardService boardService;
    private final AcademyService academyService;
    private final TeacherService teacherService;
    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/tesbro")
    public String tesbroMain(Model model, Principal principal) {
        Board latestNotice = boardService.getLastestNotice();

        model.addAttribute("latestNotice", latestNotice);

        long academyCount = academyService.countAcademyIds();
        model.addAttribute("academyCount", academyCount);
        long teacherCount = teacherService.countTeacherIds();
        model.addAttribute("teacherCount", teacherCount);
        long reviewCount = reviewService.countReviewIds();
        model.addAttribute("reviewCount", reviewCount);

        List<Review> recentReviews = reviewService.get4RecentReviews();
        model.addAttribute("recentReviews", recentReviews);

        List<Academy> mostjjimAcademy = academyService.getAcademyByVoter();
        model.addAttribute("mostjjimAcademy", mostjjimAcademy);

        if (principal != null) {
            String userAddress = userService.getUser(principal.getName()).getAddress();
            List<Academy> closerAcademyList = academyService.getCloserAcademy(userAddress);
            if (closerAcademyList.size() >= 5) {
                List<Academy> cAcademyList = academyService.overCloserAcademies(closerAcademyList); //5개 넘어갈 경우
                model.addAttribute("cAcademyList", cAcademyList);
                for (Academy academy : closerAcademyList) {
                    System.out.println(academy.getAcademyName());
                }
                return "main";
            } else {
                model.addAttribute("cAcademyList", closerAcademyList); //
            }
            return "main";
        } else{
            model.addAttribute("cAcademyList", academyService.getRecentlyAcademy());
        }
        return "main";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/tesbro";
    }
}
