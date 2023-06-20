package com.team.tesbro;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TesbroController {
    @GetMapping("/tesbro")
    public String tesbroMain() {
        return "main";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/tesbro";
    }
}
