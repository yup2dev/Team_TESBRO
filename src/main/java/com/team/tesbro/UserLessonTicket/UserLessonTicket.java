package com.team.tesbro.UserLessonTicket;

import com.team.tesbro.LessonTicket.LessonTicket;
import com.team.tesbro.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserLessonTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer usedTicketTime;
    @ManyToOne
    private SiteUser siteUser;

    @ManyToOne
    private LessonTicket lessonTicket;
}