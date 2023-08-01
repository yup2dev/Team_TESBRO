package com.team.tesbro.user_lesson_ticket;

import com.team.tesbro.lesson_ticket.LessonTicket;
import com.team.tesbro.user.SiteUser;
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