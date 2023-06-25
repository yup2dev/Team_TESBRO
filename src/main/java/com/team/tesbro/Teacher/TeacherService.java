package com.team.tesbro.Teacher;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public void create(String teacherName, String qualifications, String awards, String introduction, Academy academy) {
        Teacher teacher = new Teacher();
        teacher.setTeacherName(teacherName);
        teacher.setQualifications(qualifications);
        teacher.setAwards(awards);
        teacher.setIntroduction(introduction);
        teacher.setAcademy(academy);
        this.teacherRepository.save(teacher);
    }

    public Teacher getTeacher(Integer id) {
        Optional<Teacher> teacher = this.teacherRepository.findById(id);
        if(teacher.isPresent()) {
            return teacher.get();
        } else {
            throw new DataNotFoundException("teacher not found");
        }
    }

    public long countTeacherIds(){
        return teacherRepository.count();
    }
}
