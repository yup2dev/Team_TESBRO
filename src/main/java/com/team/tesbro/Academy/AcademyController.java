package com.team.tesbro.Academy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AcademyController {

    @GetMapping("/academy")
    @ResponseBody
    public String academy() {
        return "dd";
    }
}
