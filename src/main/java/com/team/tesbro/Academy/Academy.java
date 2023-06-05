package com.team.tesbro.Academy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private long academyNum;


}
