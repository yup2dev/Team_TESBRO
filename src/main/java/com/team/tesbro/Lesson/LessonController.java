package com.team.tesbro.Lesson;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyService;
import com.team.tesbro.Review.Review;
import com.team.tesbro.Review.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final ReviewService reviewService;
    private List<Lesson> lessonList;

    private final AcademyService academyService;
    @GetMapping("/academy/lesson")
    public String handleSelectedData(Model model) {
    lessonList = lessonService.getList();
    List<LocalDate> dateList = new ArrayList<>();

        for (Lesson lesson : lessonList) {
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
    public String detail(Model model, @PathVariable("id") Integer id, @RequestParam(value="page", defaultValue="0") int page) {
        Academy academy = this.academyService.getAcademy(id);
        model.addAttribute("academy", academy);
        Page<Review> paging = this.reviewService.getList(page);
        model.addAttribute("paging", paging);


        lessonList = lessonService.getAcLesson(id);
        List<LocalDate> dateList = new ArrayList<>();
        for (Lesson lesson : lessonList) {
            LocalDate lessonDate = lesson.getLessonDate();
            dateList.add(lessonDate);
        }
        Set<LocalDate> uniqueDates = new HashSet<>(dateList);
        List<LocalDate> uniqueDateList = new ArrayList<>(uniqueDates);
        Collections.sort(uniqueDateList);
        model.addAttribute("uniqueDateList", uniqueDateList);
        //학원의 날짜
        //시간만 손보면 끝납니다잉

        return "academy_detail";
    }


    @GetMapping("/multi_box_ajax")
    @ResponseBody
    public List<LocalTime> getLessonTimeList(@RequestParam LocalDate data) {// lessonList를 모델에서 가져옴
        List<LocalTime> lessonTimeList = lessonService.getLessonTimes(data, lessonList); // lessonList를 전달하여 호출
        return lessonTimeList;
    }


    @PostMapping("/reserve")
    @ResponseBody
    public ResponseEntity<String> reserveLesson(@RequestParam LocalDate date, @RequestParam String time) {
        // 선택한 날짜와 시간을 이용하여 예약 처리 등을 수행
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
        System.out.println(date);
        System.out.println(localTime);

        // 예약 성공 시 응답
        return ResponseEntity.ok("예약이 완료되었습니다.");
    }
}
