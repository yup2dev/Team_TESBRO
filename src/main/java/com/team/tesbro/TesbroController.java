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
        // 가장 최근 공지사항 List
        Board latestNotice = boardService.getLastestNotice();
        model.addAttribute("latestNotice", latestNotice);
        // 전체 등록된 학원 수
        long academyCount = academyService.countAcademyIds();
        model.addAttribute("academyCount", academyCount);
        // 전체 등록된 강사 수
        long teacherCount = teacherService.countTeacherIds();
        model.addAttribute("teacherCount", teacherCount);
        // 전체 등록된 리뷰 수
        long reviewCount = reviewService.countReviewIds();
        model.addAttribute("reviewCount", reviewCount);
        // 가장 최근 달린 4개의 리뷰
        List<Review> recentReviews = reviewService.get4RecentReviews();
        model.addAttribute("recentReviews", recentReviews);
        // 인기순을 출력하기 위한 학원 추천 순 List
        List<Academy> mostjjimAcademy = academyService.getAcademyByVoter();
        model.addAttribute("mostjjimAcademy", mostjjimAcademy);
        // 로그인시 사용자의 주소를 기준으로 근처 학원 List
        // 비로그인시 가장 최근에 등록된 학원 List
        if (principal != null) {
            String userAddress = userService.getUser(principal.getName()).getAddress();
            List<Academy> closerAcademyList = academyService.getCloserAcademy2(userAddress);
            model.addAttribute("cAcademyList", closerAcademyList); //
            return "main";
        } else {
            model.addAttribute("cAcademyList", academyService.getRecentlyAcademy());
        }
        return "main";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/tesbro";
    }
}