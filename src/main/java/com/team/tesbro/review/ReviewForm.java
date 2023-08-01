package com.team.tesbro.review;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewForm {
    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;

    private int star_rating;
}