package com.team.tesbro.academy;

import com.team.tesbro.lesson.LessonService;
import com.team.tesbro.review.ReviewService;
import com.team.tesbro.user.SiteUser;
import com.team.tesbro.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private final UserService userService;

    @RequestMapping("/list")
    public String academy(Model model,
                          @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "localKey", required = false) String localKey,
                          @RequestParam(value = "peopleCapacity", required = false) Integer pc) {
        // 페이지, 검색 키워드, 검색할 주소, 레슨 수용인원수
        Page<Academy> paging = this.academyService.getAcademyList(keyword, localKey, pc, page);
        model.addAttribute("paging", paging);
        // 검색 유지를 위한 키워드 modeling
        model.addAttribute("keyword", keyword);
        // 총 학원수 표시
        long count = academyService.countAcademyIds();
        model.addAttribute("academyCount", count);
        return "list";
    }

    @GetMapping("/create")
    public String getCreateAcademyForm(AcademyForm academyForm, Model model) {
        model.addAttribute("academyForm", academyForm);
        return "academy_form";
    }


    @PostMapping("/create")
    public String AcademyCreate(@Valid AcademyForm academyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "academy_form";
        }
        this.academyService.create(academyForm);
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

    // 디테일에 추가 할 학원계정 전용 검색기(관리자용 페이지 / 미완)
    @GetMapping("detail/create/search")
    public String detailSearch(Model model, @Param("keyword") String keyword) {
        List<Academy> academyList = this.academyService.getListByKeyword(keyword);
        model.addAttribute(academyList);
        return "academy_create_list";
    }


    // 학원계정 전용 파일 추가 매핑
    @GetMapping("detail/create/{id}")
    public String detailCre(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "academy_detail_form";
    }

    // 지금은 쓸데없음
    @PostMapping("detail/create/{id}")
    public String detailCreP(@PathVariable("id") Integer id, Principal principal) {
        Academy academy = academyService.getAcademy(id);
        SiteUser siteUser = userService.getUser(principal.getName());
        return String.format("redirect:academy/detail/%s", id);
    }

    //테스트용 파일 등록
    @GetMapping("/test/{id}")
    public String ttt(@PathVariable("id") Integer id) {
        return "detail_form_practice";
    }
}