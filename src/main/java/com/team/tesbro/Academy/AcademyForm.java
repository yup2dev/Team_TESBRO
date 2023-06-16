package com.team.tesbro.Academy;

import com.team.tesbro.Review.Review;
import com.team.tesbro.Teacher.Teacher;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AcademyForm {
    private String academyName;
    private String ceoName;
    private String academyTel;
    private String introduction;
    private String imgLogo;
    private String academyAddress;
}



