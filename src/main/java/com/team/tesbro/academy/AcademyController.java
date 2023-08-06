package com.team.tesbro.academy;

import com.team.tesbro.file.GenFile;
import com.team.tesbro.lesson.Lesson;
import com.team.tesbro.lesson.LessonForm;
import com.team.tesbro.lesson.LessonService;
import com.team.tesbro.review.Review;
import com.team.tesbro.review.ReviewForm;
import com.team.tesbro.review.ReviewService;
import com.team.tesbro.teacher.Teacher;
import com.team.tesbro.teacher.TeacherService;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

@RequestMapping("/academy")
@RequiredArgsConstructor
@Controller
public class AcademyController {

    private final AcademyService academyService;
    private final UserService userService;
    private final LessonService lessonService;
    private final ReviewService reviewService;

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

    //테스트용 파일 등록 하지만 이게 진짜
    @GetMapping("/test/{id}")
    public String ttt(@PathVariable("id") Integer id) {
        return "detail_form_practice";
    }

    @GetMapping("/academy/lesson")
    public String handleSelectedData(Model model) {
        List<LocalDate> dateList = new ArrayList<>();

        for (Lesson lesson : lessonService.getList()) {
            LocalDate lessonDate = lesson.getLessonDate();
            dateList.add(lessonDate);
        }
        Set<LocalDate> uniqueDates = new HashSet<>(dateList);
        List<LocalDate> uniqueDateList = new ArrayList<>(uniqueDates);
        Collections.sort(uniqueDateList);
        model.addAttribute("uniqueDateList", uniqueDateList);
        return "lesson";
    }


    @GetMapping("/academy/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, ReviewForm reviewForm, @RequestParam(value = "page", defaultValue = "0") int page) {
        Academy academy = this.academyService.getAcademy(id);
        model.addAttribute("academy", academy);
        Page<Review> paging = this.reviewService.getList(academy, page);
        model.addAttribute("paging", paging);


        List<Lesson> lessonList = lessonService.getAcLesson(id);
        List<LocalDate> dateList = new ArrayList<>();
        for (Lesson lesson : lessonList) {
            LocalDate lessonDate = lesson.getLessonDate();
            dateList.add(lessonDate);
        }
        Set<LocalDate> uniqueDates = new HashSet<>(dateList);
        List<LocalDate> uniqueDateList = new ArrayList<>(uniqueDates);
        Collections.sort(uniqueDateList);
        model.addAttribute("uniqueDateList", uniqueDateList);

        List<GenFile> fileList = academyService.getGenfileByAcademyId(Long.valueOf(id));
        Collections.sort(fileList, Comparator.comparingLong(GenFile::getId));
        String file1 = null;
        String file2 = null;
        String file3 = null;

        int fileListSize = fileList.size();
        if (fileListSize >= 1) {
            file1 = fileList.get(0).getBaseFileUri();
        }
        if (fileListSize >= 2) {
            file2 = fileList.get(1).getBaseFileUri();
        }
        if (fileListSize >= 3) {
            file3 = fileList.get(2).getBaseFileUri();
        }

        model.addAttribute("file1", file1);
        model.addAttribute("file2", file2);
        model.addAttribute("file3", file3);
        System.out.println(file1 + "/" + file2 + "/" + file3);
        return "academy_detail";
    }


    @GetMapping("/multi_box_ajax")
    @ResponseBody
    public List<LocalTime> getLessonTimeList(@RequestParam String data) {// lessonList를 모델에서 가져옴
        List<LocalTime> lessonTimeList = lessonService.getLessonTimes(LocalDate.parse(data, DateTimeFormatter.ISO_DATE), lessonService.getList()); // lessonList를 전달하여 호출
        return lessonTimeList;
    }
}