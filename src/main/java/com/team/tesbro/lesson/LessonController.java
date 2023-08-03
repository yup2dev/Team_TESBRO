package com.team.tesbro.lesson;

import com.team.tesbro.academy.Academy;
import com.team.tesbro.academy.AcademyService;
import com.team.tesbro.review.Review;
import com.team.tesbro.review.ReviewForm;
import com.team.tesbro.review.ReviewService;
import com.team.tesbro.teacher.Teacher;
import com.team.tesbro.teacher.TeacherService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final ReviewService reviewService;
    private final TeacherService teacherService;
    private final AcademyService academyService;

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


        return "academy_detail";
    }


    @GetMapping("/multi_box_ajax")
    @ResponseBody
    public List<LocalTime> getLessonTimeList(@RequestParam String data) {// lessonList를 모델에서 가져옴
        List<LocalTime> lessonTimeList = lessonService.getLessonTimes(LocalDate.parse(data, DateTimeFormatter.ISO_DATE), lessonService.getList()); // lessonList를 전달하여 호출
        return lessonTimeList;
    }

    // 레슨 생성 매핑 null값 불가능 처리 필요(아마 자바스크립트로)
    @GetMapping("/lesson/create/{id}")
    public String lessonCre(@PathVariable("id") Integer id, LessonForm lessonForm, Model model) {
        Academy academy = academyService.getAcademy(id);
        model.addAttribute("academy", academy);
        return "lesson_form";
    }

    // 래슨 생성 매핑된 부분에서 강사정보 셀렉트 한 경우 tid값으로 들어와서 lesson 저장하는 부분
    @PostMapping("/lesson/create/{Aid}/{Tid}")
    public String lessonCre2(@Valid LessonForm lessonForm, Model model, @PathVariable("Aid") Integer aid,
                             @PathVariable("Tid") Integer tid, Principal principal, BindingResult bindingResult) {
        Academy academy = this.academyService.getAcademy(aid);
        Teacher teacher = this.teacherService.getTeacher(tid);
        if (bindingResult.hasErrors()) {
            model.addAttribute("academy", academy);
            return "academy_list";
        }
        this.lessonService.createLesson(lessonForm, teacher, academy); // lessonForm, 선생, 학원
        return "redirect:/academy/list";
    }
}
