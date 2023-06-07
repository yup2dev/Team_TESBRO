package com.team.tesbro;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyRepository;
import com.team.tesbro.Academy.AcademyService;
import com.team.tesbro.Teacher.Teacher;
import com.team.tesbro.Teacher.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TesbroApplicationTests {

	@Autowired
	private AcademyService academyService;
	@Autowired
	private AcademyRepository academyRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Test
	void contextLoads() {
		Optional<Academy> oa = this.academyRepository.findById(1);
		assertTrue(oa.isPresent());
		Academy a = oa.get();

		Teacher t = new Teacher();
		t.setTeacherName("이승열");
		t.setQualifications("홈프로텍터 과정 수료");
		t.setAwards("집에 빨리가기 대회 우승");
		t.setIntroduction("안녕하세요. 왤캐 바쁘세요. 쉬엄쉬엄 일정 잘 마치시고 과제는 까먹지말고 하세요. 예외없음");
		t.setAcademy(a);
		this.teacherRepository.save(t);
	}

}
