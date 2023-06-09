package com.ll.basic1.Notice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String userName;
    // 일반 회원
    @Column(length = 200)
    private String ownerName;
    // 기업 회원
    @Column(length = 200)
    private String managerName;
    // 관리자 회원
    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 200)
    private String file;
    // 사진 같은 파일 String으로 url ??
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
