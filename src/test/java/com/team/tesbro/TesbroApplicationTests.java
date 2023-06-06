package com.team.tesbro;

import com.team.tesbro.Academy.Academy;
import com.team.tesbro.Academy.AcademyRepository;
import com.team.tesbro.Academy.AcademyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class TesbroApplicationTests {

	@Autowired
	private AcademyService academyService;
	@Test
	void contextLoads() {
		for(int i = 1; i<=10; i++) {
			String academyName = String.format("테스트[%03d] 필라테스", i);
			String ceoName= "전세린";
			String academyAddress = "대전 갈마동 271-7";
			String academyNum ="010-2556-3643";
			String introduction="안녕하세요. 테스트 필라테스입니다. 제대로 하는 건지 알수가 없어";

			this.academyService.create(academyName, ceoName, academyAddress, academyNum, introduction);
		}
	}

}
