package com.team.tesbro.lesson_res;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.lesson.Lesson;
import com.team.tesbro.lesson.LessonRepository;
import com.team.tesbro.user.SiteUser;
import com.team.tesbro.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Lesson_ResService {
    private final LessonRepository lessonRepository;
    private final Lesson_ResRepository lessonResRepository;
    private final UserRepository userRepository;

    public Optional<Lesson> findLessonId(LocalDate date, LocalTime time, Integer id) {
        return lessonRepository.findIdByLessonDateAndLessonTimeAndAcademyId(date, time, id);
    }

    //현재 인원 증가시키는 로직 팝업 띄우는 로직 추가해야함 controller에는 다 구현 되어있음
    public void increaseLessonCapacity(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("레슨 없음 id : " + lessonId));

        if (lesson.getCurrentCapacity() < lesson.getPeopleCapacity()) {
            lesson.setCurrentCapacity(lesson.getCurrentCapacity() + 1);
            lessonRepository.save(lesson);
        } else {
            throw new IllegalStateException("예약 꽉참");
        }
    }

    //레슨 인원 포과하는지 보는 함수 근데 팝업으로 수정해서 service로 빼야함 controller에는 다 구현 되어있음
    public boolean isLessonFullyBooked(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("레슨 없음 id : " + lessonId));
        return lesson.getCurrentCapacity() >= lesson.getPeopleCapacity();
    }

    public void reserve(Lesson_ResDto lessonResDto, Integer lessonId, Integer siteUserId){
        Optional<Lesson> lesson1 = lessonRepository.findById(lessonId);
        Lesson lesson = lessonRepository.findById(lesson1.get().getId())
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 레슨ID입니다"));

        Lesson_Res lessonRes = new Lesson_Res();
        lessonRes.setLesson(lesson);
        lessonRes.setBookDate(LocalDateTime.now());

        List<Integer> bookedUsersId = new ArrayList<>();
        bookedUsersId.add(siteUserId);
        lessonResDto.setBookedUsersId(bookedUsersId);

        for (Integer userId : lessonResDto.getBookedUsersId()) {
            Optional<SiteUser> userOptional = userRepository.findById(userId);
            SiteUser user = userOptional.orElseThrow(() -> new DataNotFoundException("존재하지 않는 사용자 ID입니다"));
            lessonRes.getUsers().add(user);
        }
        lessonResRepository.save(lessonRes);
    }

    public List<Lesson_Res> findUserRes(Integer userId){
        return lessonResRepository.findByUsersId(userId, Sort.by(Sort.Direction.DESC, "lesson.lessonDate"));
    }

    public List<Integer> findUsers(Integer lessonId){
        return lessonResRepository.findUserIdsByLessonId(lessonId);
    }
}