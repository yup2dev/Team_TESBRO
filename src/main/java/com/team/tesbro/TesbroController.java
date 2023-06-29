package com.team.tesbro;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyService;
import com.team.tesbro.Board.Board;
import com.team.tesbro.Board.BoardService;
import com.team.tesbro.Review.Review;
import com.team.tesbro.Review.ReviewService;
import com.team.tesbro.Teacher.TeacherService;
import com.team.tesbro.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            List<Academy> cAcademyList = new ArrayList<>();
            if (closerAcademyList.size() >= 5) {
                cAcademyList = academyService.overAcademies(closerAcademyList); //5개 오버하면 처리해주는거
                model.addAttribute("cAcademyList", cAcademyList);
                for (Academy academy : closerAcademyList) {
                    System.out.println(academy.getAcademyName());
                }
                return "main";
            } else {
                cAcademyList = academyService.getRecentlyAcademy();
                model.addAttribute("cAcademyList", cAcademyList); //
                for (Academy academy : closerAcademyList) {
                    System.out.println(academy.getAcademyName());
                }
            }
            return "main";
        }
        return "main";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/tesbro";
    }
}
