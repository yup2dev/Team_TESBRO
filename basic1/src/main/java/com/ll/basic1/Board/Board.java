package com.ll.basic1.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String boardCategory;

    @Column(length = 200)
    private String managerName;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Answer> answers;

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
        answer.setBoard(this);
    }
}


