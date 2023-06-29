package com.team.tesbro.User;

import com.team.tesbro.Board.Board;
import com.team.tesbro.Board.BoardForm;
import com.team.tesbro.Board.BoardService;
import com.team.tesbro.Review.ReviewService;
import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class    UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final ReviewService reviewService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm, Model model) {
        return "signup_form";
    }
    @GetMapping("/sign")
    public String sign(){
        return "Bnum";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            UserRole role = userCreateForm.getUsername().startsWith("admin") ? UserRole.ADMIN : UserRole.USER;

            userService.create(userCreateForm.getUsername(),

                    userCreateForm.getEmail(), userCreateForm.getPassword1(), role, userCreateForm.getAddress());

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/tesbro";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/signup/mail")
    public String mail() {
        return "mail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my")
    public String my(Model model, Principal principal) {
        userService.getUser(principal.getName()).getUsername();
        model.addAttribute("siteUser", userService.getUser(principal.getName()));
        return "my";
    }
}
