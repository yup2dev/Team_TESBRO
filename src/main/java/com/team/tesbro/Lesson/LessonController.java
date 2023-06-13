package com.team.tesbro.Lesson;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;


    @GetMapping("/academy/lesson")
    public String handleSelectedData(Model model) {
        List<Lesson> lessonList = lessonService.getList();
        model.addAttribute("lessonList", lessonList);
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
