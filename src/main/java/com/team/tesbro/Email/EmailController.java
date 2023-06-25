package com.team.tesbro.Email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/mailConfirm")
    @ResponseBody
    public String mailConfirm(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        System.out.println(email);
        String authCode = emailService.sendEmail(email);
        return authCode;
    }
}