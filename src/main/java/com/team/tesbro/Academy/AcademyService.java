package com.team.tesbro.Academy;

import com.team.tesbro.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AcademyService {
    private final AcademyRepository academyRepository;

    public void create(String academyName, String ceoName, String academyAddress, String academyNum, String introduction) {
        Academy academy = new Academy();
        academy.setAcademyName(academyName);
        academy.setCeoName(ceoName);
        academy.setAcademyAddress(academyAddress);
        academy.setAcademyNum(academyNum);
        academy.setIntroduction(introduction);
        academy.setCreateDate(LocalDateTime.now());
        this.academyRepository.save(academy);
    }

    public Academy getAcademy(Integer id) {
        Optional<Academy> academy = this.academyRepository.findById(id);
        if(academy.isPresent()) {
            return academy.get();
        } else {
            throw new DataNotFoundException("academy not found");
        }
    }

}
