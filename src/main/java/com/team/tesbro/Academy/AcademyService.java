package com.team.tesbro.Academy;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

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

    public void create(String academyName, String ceoName, String academyAddress, String academyTel, String introduction, String imgLogo, Long corNum) {
        Academy academy = new Academy();
        academy.setAcademyName(academyName);
        academy.setCeoName(ceoName);
        academy.setAcademyAddress(academyAddress);
        academy.setAcademyTel(academyTel);
        academy.setIntroduction(introduction);
        academy.setImgLogo(imgLogo);
        academy.setCorNum(corNum);

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

    public List<Academy> getCloserAcademy(String address) {
        //스플릿으로 주소 나누기
        String splitNum = "";
        String[] splitAddress = address.split(" ");
        List<Academy> academyList = new ArrayList<>();
        String[] keyWordArray = new String[splitAddress.length];
        // 글자 나눠서 순서대로 검색할 배열 만들기
        for (int i = 0; i < splitAddress.length; i++) {
            splitNum += splitAddress[i] + " ";
            keyWordArray[i] = splitNum;
            System.out.println(splitNum);
        }
        // 키워드 거꾸로 돌리고 마지막 띄워쓰기 없앰
        List<String> reversedKeywords = new ArrayList<>(Arrays.asList(keyWordArray));
        reversedKeywords.replaceAll(keyword -> keyword.trim());
        Collections.reverse(reversedKeywords);
        // 일단 다 들고와
        // 순서대로 배열에 넣고 싶었는데
        List<Academy> matchingAcademies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i >= reversedKeywords.size()) {
                break;
            }
            String keyword = reversedKeywords.get(i);
            matchingAcademies.addAll(academyRepository.findByAcademyAddressContaining(keyword));
        }

        Set<Academy> uniqueAcademies = new HashSet<>(matchingAcademies);

        List<Academy> sortedAcademies = new ArrayList<>(uniqueAcademies);
        Collections.sort(sortedAcademies, new Comparator<Academy>() {
            @Override
            public int compare(Academy a1, Academy a2) {
                int overlapCount1 = getOverlapCount(a1, reversedKeywords);
                int overlapCount2 = getOverlapCount(a2, reversedKeywords);
                return Integer.compare(overlapCount2, overlapCount1);
            }

            private int getOverlapCount(Academy academy, List<String> reversedKeywords) {
                int count = 0;
                for (String keyword : reversedKeywords) {
                    if (academy.getAcademyAddress().contains(keyword)) {
                        count++;
                    }
                }
                return count;
            }
        });
        return sortedAcademies;
    }

    public long countAcademyIds() {
        return academyRepository.count();
    }


    public List<Academy> getAcademyByVoter() {
        return academyRepository.findMostjjimAcademy();
    }

    // 왜 전체 주석이 안되지
    public List<Academy> getCloserAcademy2(String address) {
        //split
        String splitNum = "";
        String[] splitAddress = address.split(" ");
        List<Academy> academyList = new ArrayList<>();
        String[] keyWordArray = new String[splitAddress.length];

        for (int i = 0; i < splitAddress.length; i++) {
            splitNum += splitAddress[i] + " ";
            keyWordArray[i] = splitNum;
            System.out.println(splitNum);
        }

        List<String> reversedKeywords = new ArrayList<>(Arrays.asList(keyWordArray));
        reversedKeywords.replaceAll(keyword -> keyword.trim());
        Collections.reverse(reversedKeywords);

        for (int i = 0; i < 5; i++) {
            if (i >= reversedKeywords.size()) {
                break;
            }
            String keyword = reversedKeywords.get(i);
            List<Academy> matchingAcademies = academyRepository.findByAcademyAddressContaining(keyword);
            for(int j = 0; j <= matchingAcademies.size(); j++){
                if (j >= matchingAcademies.size()) {
                    break;
                }
                academyList.add(matchingAcademies.get(j));
            }
        }
        List<Academy> uniqueAcademies = new ArrayList<>(new HashSet<>(academyList));
        // 여기 데이터가 일치하는 수가 많은 순으로 정렬이 필요하다
        // 원래 구현하려는 기능 => 첫번째 인덱스로 조회해서 있으면 배열에 담고, 2번재 인덱스로 조회해서 있으면 담고 (그런데 한번에 여러개의 데이터가 조회되는 경우에는 처리하는 방식이
        //      어떻게 되어야 할 지 잘 감이 안잡힘 ==> for문으로 해당 리스트를 또 돌려서 추가해준다??
        Collections.reverse(uniqueAcademies);
        return uniqueAcademies;
    }

    public List<Academy> overAcademies(List<Academy> list){
        Academy[] academies = new Academy[5];
        for(int i = 0; i<= academies.length; i++){
            if (i >= 5) {
                break;
            }
            academies[i] = list.get(i);
        }
        return List.of(academies);
    }
}



