package com.team.tesbro;

import com.team.tesbro.Email.EmailAuthRequestDto;
import com.team.tesbro.Email.EmailService;
import com.team.tesbro.file.GenFile;
import com.team.tesbro.file.GenFileRepository;
import com.team.tesbro.file.GenFileService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
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
        GenFile afile = genFileService.getGenFileById(1L);
        String file = afile.getBaseFileUri();
        model.addAttribute("file", file);
        return "uploadShow";
    }

    @GetMapping("/juso")
    public String jusoinput(){
        return "addr";
    }

    @GetMapping("/mail")
    public String email(){
        return "mail";
    }
}
