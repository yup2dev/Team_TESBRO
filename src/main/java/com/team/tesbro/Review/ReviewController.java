package com.team.tesbro.Review;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final AcademyService academyService;
    private final ReviewService reviewService;

    @PostMapping("/create/{id}")
    public String createReview(Model model, @PathVariable("id") Integer id, @RequestParam String content, @RequestParam int star_rating){
        Academy academy = this.academyService.getAcademy(id);
        this.reviewService.create(academy, content, star_rating);
        return String.format("redirect:/academy/detail/%s", id);
    }
}
