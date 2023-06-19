package com.team.tesbro.Review;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyService;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final AcademyService academyService;
    private final ReviewService reviewService;
    private final UserService userService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String reviewCreate(Model model, @PathVariable("id") Integer id, @Valid ReviewForm reviewForm, BindingResult bindingResult, Principal principal){
        Academy academy = this.academyService.getAcademy(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("academy", academy);
            return "academy_detail";
        }
        this.reviewService.create(academy, reviewForm.getContent(), reviewForm.getStar_rating(), siteUser);
        return String.format("redirect:/academy/detail/%s", id);
    }
}
