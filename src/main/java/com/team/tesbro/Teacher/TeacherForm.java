package com.team.tesbro.Teacher;

import com.team.tesbro.Academy.Academy;
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