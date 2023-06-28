package com.team.tesbro.LessonTicket;


import com.team.tesbro.User.SiteUser;
import com.team.tesbro.UserLessonTicket.UserLessonTicket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class LessonTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String ticketName;
    @Column
    private Integer ticketPrice;
    @Column
    private Integer lessonTime;

    @OneToMany(mappedBy = "lessonTicket", cascade = CascadeType.REMOVE)
    private List<UserLessonTicket> userLessonTickets = new ArrayList<>();
}