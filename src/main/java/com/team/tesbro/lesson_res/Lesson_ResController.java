package com.team.tesbro.lesson_res;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class Lesson_ResController {
    private final Lesson_ResService lesson_resService;

    @PostMapping("/reserve")
    public String reserveLesson(@RequestParam String datePicker, @RequestParam String childSelectBox) {
        // 선택한 날짜와 시간을 이용하여 예약 처리 등을 수행;
        LocalDate date = LocalDate.parse(datePicker, DateTimeFormatter.ISO_DATE);
        LocalTime time = LocalTime.parse(childSelectBox, DateTimeFormatter.ISO_TIME);
        // 예약 성공 시 응답
        System.out.println(date);
        System.out.println(time);
        return "redirect:reserve";
    }

    @GetMapping("/reserve")
    public String onlesson(Model model) {
        return "reserve";
    }
}
