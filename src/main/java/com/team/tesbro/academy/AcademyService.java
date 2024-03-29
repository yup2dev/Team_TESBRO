package com.team.tesbro.academy;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.file.GenFile;
import com.team.tesbro.file.GenFileRepository;
import com.team.tesbro.user.SiteUser;
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
    private final GenFileRepository genFileRepository;

    public List<Academy> getListByKeyword(String keyword) {
        if (keyword != null) {
            return academyRepository.findByAcademyNameContaining(keyword);
        }
        return this.academyRepository.findAll();
    }

    public void create(AcademyForm academyForm) {
        Academy academy = new Academy();
        academy.setAcademyName(academyForm.getAcademyName());
        academy.setCeoName(academyForm.getCeoName());
        academy.setAcademyAddress(academyForm.getAcademyAddress());
        academy.setAcademyTel(academyForm.getAcademyTel());
        academy.setIntroduction(academyForm.getIntroduction());
        academy.setImgLogo(academy.getImgLogo());
        academy.setCorNum(academyForm.getCorNum());
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
        // 조회될 수 있는 경우의 수를 고려하여 Repository에서 조회
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
        return academyRepository.findMostJjimAcademy();
    }

    public List<Academy> getRecentlyAcademy() {
        return academyRepository.findMostRecentlyAcademy();
    }

    // 주소로 유사도 찾기 ver2
    public List<Academy> getCloserAcademy2(String address) {
        // 주소를 split해서 쪼갬
        String splitNum = "";
        String[] splitAddress = address.split(" ");
        List<Academy> academyList = new ArrayList<>();
        String[] keyWordArray = new String[splitAddress.length];

        for (int i = 0; i < splitAddress.length; i++) {
            splitNum += splitAddress[i] + " ";
            keyWordArray[i] = splitNum;
            System.out.println(splitNum);
        }
        // 키워드 거꾸로 돌리고 마지막 띄워쓰기 없앰
        List<String> reversedKeywords = new ArrayList<>(Arrays.asList(keyWordArray));
        reversedKeywords.replaceAll(keyword -> keyword.trim());
        Collections.reverse(reversedKeywords);

        for (int i = 0; i < 5; i++) {
            if (i >= reversedKeywords.size()) {
                break;
            }
            String keyword = reversedKeywords.get(i);
            List<Academy> matchingAcademies = academyRepository.findByAcademyAddressContaining(keyword);
            // 최대 5개 까지만
            academyList.addAll(matchingAcademies.subList(0, Math.min(5, matchingAcademies.size())));
        }

        List<Academy> uniqueAcademies = new ArrayList<>(new HashSet<>(academyList));
        Collections.reverse(uniqueAcademies);
        Collections.sort(uniqueAcademies, new Comparator<Academy>() {
            @Override
            public int compare(Academy a1, Academy a2) {
                int overlapCount1 = getOverlapCount(a1, reversedKeywords);
                int overlapCount2 = getOverlapCount(a2, reversedKeywords);

                // 겹치는 횟수가 많은 순서대로 정렬
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
        return uniqueAcademies;
    }


    public List<GenFile> getGenfileByAcademyId(Long id) {
        return genFileRepository.findByAcademyId(id);
    }
}



