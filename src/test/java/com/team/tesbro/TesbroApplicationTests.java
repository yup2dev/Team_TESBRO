package com.team.tesbro;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyRepository;
import com.team.tesbro.Academy.AcademyService;
import com.team.tesbro.Review.Review;
import com.team.tesbro.Review.ReviewService;
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

	@Autowired
	private ReviewService reviewService;


	@Test
	void contextLoads() {
		Optional<Academy> oa = this.academyRepository.findById(1);
		assertTrue(oa.isPresent());
		Academy a = oa.get();

		Teacher t = new Teacher();
		t.setTeacherName("전세린");
		t.setQualifications("수상한 경력");
		t.setAwards("의심스러운 수상내역");
		t.setIntroduction("노는게 제일 좋아. 친구들 모여라. 언제나 즐거워 개구쟁이 뽀로로.");
		t.setAcademy(a);
		this.teacherRepository.save(t);

		Teacher t2 = new Teacher();
		t2.setTeacherName("김다은");
		t2.setQualifications("카페 운영 56년차");
		t2.setAwards("클라이언트블락 자격증 보유");
		t2.setIntroduction("눈 덮인 숲속마을. 꼬마 펭귄 나가신다. 언제나 즐거워 오늘은 또 무슨 일이 생길까.");
		t2.setAcademy(a);
		this.teacherRepository.save(t2);

		Teacher t3 = new Teacher();
		t3.setTeacherName("김상엽");
		t3.setQualifications("다리 분쇄 2주차");
		t3.setAwards("한발 멀리뛰기 대회 금메달 수상");
		t3.setIntroduction("뽀로로를 불러봐요. 뽀롱뽀롱 뽀로로. 뽀롱뽀롱 뽀로로. 뽀롱뽀롱뽀롱뽀롱뽀롱뽀롱뽀롱 뽀로로.");
		t3.setAcademy(a);
		this.teacherRepository.save(t3);

		Teacher t4 = new Teacher();
		t4.setTeacherName("이승열");
		t4.setQualifications("홈프로텍터 과정 수료");
		t4.setAwards("집에 빨리가기 대회 우승");
		t4.setIntroduction("노는게 제일 좋아. 친구들 모여라. 언제나 즐거워. 뽀롱뽀롱뽀롱뽀롱 뽀 로 로!");
		t4.setAcademy(a);
		this.teacherRepository.save(t4);
	}

	@Test
	void contextLoads2() {
		Optional<Academy> oa = this.academyRepository.findById(2);
		assertTrue(oa.isPresent());
		Academy a = oa.get();

		Teacher t = new Teacher();
		t.setTeacherName("테스트");
		t.setQualifications("테스트 경력");
		t.setAwards("테스트 수상내역");
		t.setIntroduction("테스트 자기소개 으아아아아아악");
		t.setAcademy(a);
		this.teacherRepository.save(t);

	}

	@Test
	void contextLoads3() {


		for (int i = 1; i <= 20; i++){
			Optional<Academy> oa = this.academyRepository.findById(1);
			assertTrue(oa.isPresent());
			Academy a = oa.get();

			String content = String.format("테스트 리뷰:[%03d]", i);
			int star_rating = 5;

			this.reviewService.create(a, content, star_rating, null);
		}
	}
}
