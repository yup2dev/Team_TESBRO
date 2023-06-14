package com.team.tesbro.Lesson;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private List<LocalDate> uniqueDateList;
    private List<Lesson> lessonTimeList;
    private List<LocalTime> uniqueTimeList;

    @GetMapping("/academy/lesson")
    public String handleSelectedData(Model model) {

        List<Lesson> lessonList = lessonService.getList();
        List<LocalDate> dateList = new ArrayList<>();
        for (Lesson lesson : lessonList) {
            LocalDate lessonDate = lesson.getLessonDate();
            dateList.add(lessonDate);
        }
        Set<LocalDate> uniqueDates = new HashSet<>(dateList);
        uniqueDateList = new ArrayList<>(uniqueDates);
        Collections.sort(uniqueDateList);
        model.addAttribute("uniqueDateList", uniqueDateList);
        // 데이터 날짜 정렬 및 모델에 출력

        if (lessonTimeList == null || lessonTimeList.isEmpty()) {
            // lessonTimeList가 null 또는 비어 있는 경우 처리
            uniqueTimeList = new ArrayList<>();
            uniqueTimeList.add(LocalTime.of(0, 0));
            model.addAttribute("uniqueTimeList", uniqueTimeList);
        } else {
            // lessonTimeList에 값이 있는 경우 처리
            List<LocalTime> timeList = new ArrayList<>();
            for (Lesson lesson2 : lessonTimeList) {
                LocalTime lessonTime = lesson2.getLessonTime();
                timeList.add(lessonTime);
            }

            Set<LocalTime> uniqueTimes = new HashSet<>(timeList);
            System.out.println(uniqueTimes);
            uniqueTimeList = new ArrayList<>(uniqueTimes);
            Collections.sort(uniqueTimeList);
            model.addAttribute("uniqueTimeList", uniqueTimeList);
        }
        // 데이터 날짜 값 받아서 시간 배열 만드는 로직
        return "lesson";
    }

    @GetMapping("/multi_box_ajax")
    @ResponseBody
    public List<Lesson> selectedData2(@RequestParam LocalDate data) {
        lessonTimeList = lessonService.getTimeList(data);
        System.out.println(data);

        Iterator<Lesson> iterator = lessonTimeList.iterator();

        while (iterator.hasNext()) {
            Lesson element = iterator.next();
            System.out.println(element);
        }
        return lessonTimeList;
    }

//    @GetMapping("/multi_box_ajax")
//    @ResponseBody
//    public List<Lesson> selectedData3(Model model, LocalDate data){
//        lessonTimeList = lessonService.getTimeList(data);
//        Iterator<Lesson> iterator = lessonTimeList.iterator();
//
//        while (iterator.hasNext()) {
//            Lesson element = iterator.next();
//            System.out.println(element);
//        }
//        return lessonTimeList;
//    }
}