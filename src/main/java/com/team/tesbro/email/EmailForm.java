package com.team.tesbro.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailForm {
    @NotEmpty(message = "이메일을 입력해주세요")
    public String email;
}