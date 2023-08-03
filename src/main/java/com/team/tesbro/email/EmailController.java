package com.team.tesbro.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private String authCode;

    @PostMapping("/mailConfirm")
    @ResponseBody
    public String mailConfirm(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        authCode = emailService.sendEmail(email);
        return authCode;
    }

    @PostMapping("/mailConfirm/check")
    @ResponseBody
    public boolean checkVerificationCode(@RequestParam("verificationCode") String verificationCode) {
        // 이메일 일치 여부 확인
        boolean isValid = verificationCode.equals(authCode);
        return isValid;
    }
}