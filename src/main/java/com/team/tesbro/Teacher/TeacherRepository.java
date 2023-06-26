package com.team.tesbro.Teacher;

import com.team.tesbro.Academy.Academy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Page<Teacher> findAllByAcademy(Academy academy, Pageable pageable);
}
