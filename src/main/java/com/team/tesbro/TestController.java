package com.team.tesbro;

import com.team.tesbro.academy.AcademyService;
import com.team.tesbro.file.GenFile;
import com.team.tesbro.file.GenFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final AcademyService academyService;
    private final GenFileService genFileService;

    @RequestMapping("/upload")
    public String upload() {
        return "upload";
    }

    @RequestMapping("/upload/show")
    public String showfile(Model model) {
        GenFile afile = genFileService.getGenFileById(1L);
        String file = afile.getBaseFileUri();
        model.addAttribute("file", file);
        return "uploadShow";
    }

    @GetMapping("/juso")
    public String jusoinput() {
        return "addr";
    }

    @GetMapping("/mail")
    public String email() {
        return "mail";
    }

    @GetMapping("/testC")
    @ResponseBody
    public void avd(){
        academyService.getCloserAcademy("서울 강남구 논현로164길 6");
    }
}
