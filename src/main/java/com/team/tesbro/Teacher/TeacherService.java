package com.team.tesbro.Teacher;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.Review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public void create(String teacherName, String introduction, String qualifications, String awards, Academy academy) {
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


    public long countTeacherIds() {
        return teacherRepository.count();
    }

    public Page<Teacher> getList(Academy academy, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.teacherRepository.findAllByAcademy(academy, pageable);

    }
}
