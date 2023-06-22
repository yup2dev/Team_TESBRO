package com.team.tesbro;

import com.team.tesbro.file.GenFile;
import com.team.tesbro.file.GenFileRepository;
import com.team.tesbro.file.GenFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final GenFileService genFileService;
    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }
    @RequestMapping("/upload/show")
    public String showfile(Model model){
        GenFile afile = genFileService.getGenFileById(6L);
        String file = afile.getBaseFileUri();
        model.addAttribute("file", file);

        return "uploadShow";
    }
}
