package com.team.tesbro.academy;

import com.team.tesbro.lesson.Lesson;
import com.team.tesbro.review.Review;
import com.team.tesbro.teacher.Teacher;
import com.team.tesbro.file.GenFile;
import com.team.tesbro.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Academy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String academyName;

    @Column(length = 100)
    private String ceoName;

    @Column
    private String academyTel;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(columnDefinition = "TEXT")
    private String imgLogo;

    private LocalDateTime createDate;

    @Column
    private String academyAddress;

    @Column
    private Integer jjim;

    @Column
    private Long corNum;

    @OneToMany(mappedBy = "academy", cascade = CascadeType.REMOVE)
    private List<Teacher> teacherList;

    @OneToMany(mappedBy = "academy", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "academy", cascade = CascadeType.REMOVE)
    private List<Lesson> lessonList;

    @OneToMany(mappedBy = "academy")
    private List<GenFile> genFiles;

    @ManyToMany
    Set<SiteUser> voter;

    public void updateJjim() {
        this.jjim = this.voter.size();
    }
}