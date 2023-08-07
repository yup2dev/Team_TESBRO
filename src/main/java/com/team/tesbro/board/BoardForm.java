package com.team.tesbro.board;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    private String category;
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 30)
    private String subject;
    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}