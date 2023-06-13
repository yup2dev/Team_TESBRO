package com.ll.basic1.Academy;

import com.ll.basic1.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AcademyService {
    private final AcademyRepository academyRepository;

    public List<Academy> getList(String keyword) {
        if(keyword != null){
            return academyRepository.findByAcademyNameContaining(keyword);
        }
        return this.academyRepository.findAll();
    }

    public void create(Integer jjim, String academyAddress ,String academyName, String ceoName, String academySi, String academyGu, String academyDong, String academyTel, String introduction, String imgLogo) {
        Academy academy = new Academy();
        academy.setAcademyName(academyName);
        academy.setAcademyAddress(academyAddress);
        academy.setCeoName(ceoName);
        academy.setAcademySi(academySi);
        academy.setAcademyGu(academyGu);
        academy.setAcademyDong(academyDong);
        academy.setAcademyTel(academyTel);
        academy.setIntroduction(introduction);
        academy.setImgLogo(imgLogo);
        academy.setJjim(jjim);
        academy.setCreateDate(LocalDateTime.now());
        this.academyRepository.save(academy);
    }

    public Academy getAcademy(Integer id) {
        Optional<Academy> academy = this.academyRepository.findById(id);
        if (academy.isPresent()) {
            return academy.get();
        } else {
            throw new DataNotFoundException("academy not found");
        }
    }
}
