package com.team.tesbro.Academy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private String academyAddress;

    @Column
    private String academyNum;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private LocalDateTime createDate;

    //업체아이디, 수업리스트, 강사리스트 <클래스가 없음
    // 찜하기 .. 후기? 사진 < 방법 모름
}
