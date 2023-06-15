package com.ll.basic1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ajax {
    private int count1 = 0;
    private int count2 = 0;

    @GetMapping("/ajax")
    public String showMain1(Model model) {
        count1++;
        count2++;
        model.addAttribute("count1", count1);
        model.addAttribute("count2", count2);
        return "ajax";
    }

    @GetMapping("/count1")
    @ResponseBody
    public int getCount1(Model model){
        model.addAttribute("count1", count1);
        return count1++;
    }
}