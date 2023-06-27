package com.team.tesbro.Academy;

import com.team.tesbro.Board.Board;
import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Academy> getAcademyList(String order,int page) {
        Sort sort;
        if (order.equals("voter")) {
            sort = Sort.by(Sort.Direction.DESC, "voter");
        } else if (order.equals("review")) {
            sort = Sort.by(Sort.Direction.DESC, "review");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "id");
        }
        Pageable pageable = PageRequest.of(page, 5);
        return this.academyRepository.findAll(pageable);
    }


    public long countAcademyIds() {
        return academyRepository.count();
    }


    public List<Academy> getAcademyByVoter() {
        return academyRepository.findMostjjimAcademy();
    }
}
