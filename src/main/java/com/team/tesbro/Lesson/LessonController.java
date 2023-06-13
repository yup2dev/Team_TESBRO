package com.team.tesbro.Lesson;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;


    @GetMapping("/academy/lesson")
    public String handleSelectedData(Model model) {
        List<Lesson> lessonList = lessonService.getList();
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

    @GetMapping("/multi_box_ajax")
    public String selectedData2(Model model, @RequestParam("data") LocalDate data) {
        List<Lesson> lessonTimeList = lessonService.getTimeList(data);
        System.out.println(data);

        Iterator<Lesson> iterator = lessonTimeList.iterator();

        while (iterator.hasNext()) {
            Lesson element = iterator.next();
            System.out.println(element);
        }

        model.addAttribute("lessonTimeList", lessonTimeList);
        return "lesson";
    }
}
