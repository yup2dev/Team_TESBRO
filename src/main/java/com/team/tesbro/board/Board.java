package com.team.tesbro.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.team.tesbro.user.SiteUser;
import com.team.tesbro.file.GenFile;
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

    @ManyToOne
    private SiteUser author;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private LocalDateTime updateDate;

    @Column(name = "views")
    private Integer views;

    @Column(columnDefinition = "TEXT")
    private String picture;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToMany
    @JoinTable(name = "GEN_FILE_BOARDS",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "gen_file_id"))
    private List<GenFile> genFiles;

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answer);
        answer.setBoard(this);
    }

    public Integer getViews() {
        return views != null ? views : 0;
    }

    @ManyToMany
    Set<SiteUser> voter;
}


