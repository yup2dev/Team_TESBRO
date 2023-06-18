package com.team.tesbro.lesson_res;

import com.team.tesbro.DataNotFoundException;
import com.team.tesbro.Lesson.Lesson;
import com.team.tesbro.Lesson.LessonRepository;
import com.team.tesbro.User.SiteUser;
import com.team.tesbro.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public boolean isLessonFullyBooked(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("레슨 없음 id : " + lessonId));
        return lesson.getCurrentCapacity() >= lesson.getPeopleCapacity();
    }

//    private Integer getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof SiteUser) {
//            SiteUser user = (SiteUser) authentication.getPrincipal();
//            return user.getId();
//        }
//        return null;
//    }

    public void reserve(Lesson_ResDto lessonResDto, Principal principal){

        Lesson lesson = lessonRepository.findById(lessonResDto.getLessonId())
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 레슨ID입니다"));

        Lesson_Res lessonRes = new Lesson_Res();
        lessonRes.setLesson(lesson);
        lessonRes.setBookDate(LocalDateTime.now());
        lessonResRepository.save(lessonRes);

        List<Integer> userList = lessonResDto.getBookedUsersId();
        List<SiteUser> users = userRepository.findAllById(userList);

        Optional<SiteUser> currentUsername = userRepository.findByusername(principal.getName());
        Integer currentUserId = currentUsername.get().getId();
        if (currentUserId != null) {
            SiteUser currentUser = userRepository.findById(currentUserId).orElse(null);
            if (currentUser != null) {
                users.add(currentUser);
            }
        }

        lessonRes.getUsers().addAll(users);
        lessonResRepository.save(lessonRes);
    }
}