package com.team.tesbro.teacher;

import com.team.tesbro.academy.Academy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherForm {
    private String teacherName;
    private String qualifications;
    private String awards;
    private String introduction;
    private Academy academy;
    private String imgLogo;
}