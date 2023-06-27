package com.team.tesbro.Academy;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AcademyService {
    private final AcademyRepository academyRepository;

    public List<Academy> getList(String keyword) {
        if (keyword != null) {
            return academyRepository.findByAcademyNameContaining(keyword);
        }
        return this.academyRepository.findAll();
    }

    public void create(String academyName, String ceoName, String academyAddress, String academyTel, String introduction, String imgLogo) {
        Academy academy = new Academy();
        academy.setAcademyName(academyName);
        academy.setCeoName(ceoName);
        academy.setAcademyAddress(academyAddress);
        academy.setAcademyTel(academyTel);
        academy.setIntroduction(introduction);
        academy.setImgLogo(imgLogo);

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

    public void vote(Academy academy, SiteUser siteUser) {
        academy.getVoter().add(siteUser);
        academy.updateJjim();
        this.academyRepository.save(academy);
    }

    public Page<Academy> getAcademyList(String keyword, String localKey, Integer peopleCapacity, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        if (StringUtils.hasText(keyword) && StringUtils.hasText(localKey) && peopleCapacity != null) {
            return academyRepository.searchByKLC(keyword, localKey, peopleCapacity, pageable);
        } else if (StringUtils.hasText(keyword) && StringUtils.hasText(localKey)) {
            return academyRepository.searchByKL(keyword, localKey, null, pageable);
        } else if (StringUtils.hasText(keyword) && peopleCapacity != null) {
            return academyRepository.searchByC(keyword, null, peopleCapacity, pageable);
        } else if (StringUtils.hasText(localKey) && peopleCapacity != null) {
            return academyRepository.searchByC(null, localKey, peopleCapacity, pageable);
        } else if (StringUtils.hasText(keyword)) {
            return academyRepository.searchByKL(keyword, null, null, pageable);
        } else if (StringUtils.hasText(localKey)) {
            return academyRepository.searchByKL(null, localKey, null, pageable);
        } else if (peopleCapacity != null) {
            return academyRepository.searchByC(null, null, peopleCapacity, pageable);
        }
        return academyRepository.findAll(pageable);
    }




    public long countAcademyIds() {
        return academyRepository.count();
    }

    public List<Academy> getAcademyByVoter() {
        return academyRepository.findMostjjimAcademy();
    }
}
