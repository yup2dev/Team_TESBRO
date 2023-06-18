package com.team.tesbro.lesson_res;

import com.team.tesbro.Lesson.Lesson;
import com.team.tesbro.User.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Lesson_ResDto {
    @NotNull
    private Integer lessonId;
    @NotNull
    private List<Integer> bookedUsersId;
}
