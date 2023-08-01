package com.team.tesbro.review;

import com.team.tesbro.academy.Academy;
import com.team.tesbro.user.SiteUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @Column
    @Max(5)
    @Min(1)
    private int star_rating;

    @ManyToOne
    private SiteUser userId;

    @ManyToOne
    private Academy academy;

    @ManyToMany
    Set<SiteUser> voter;
}
