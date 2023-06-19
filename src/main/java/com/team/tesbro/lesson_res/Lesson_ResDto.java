package com.team.tesbro.lesson_res;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Lesson_ResDto {
    @NotNull
    private List<Integer> bookedUsersId;
}
